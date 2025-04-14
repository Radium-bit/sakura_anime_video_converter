# 第一阶段：使用 Maven 编译 Java 项目
FROM maven:3.9.6-openjdk-17 AS builder

# 设置工作目录
WORKDIR /build

# 复制项目文件到容器中
COPY . .

# 执行 Maven 构建，跳过测试
RUN mvn clean package -DskipTests

# 第二阶段：构建运行时镜像
FROM azul/zulu-openjdk-alpine:17-jre

# 安装 FFmpeg
RUN apk add --no-cache ffmpeg

# 设置工作目录
WORKDIR /run

# 从构建阶段复制构建产物到运行目录
# 使用通配符匹配 jar 文件
COPY --from=builder /build/target/VideoConverter-*.jar /run/app.jar

# 检查 application.properties 是否存在，如果不存在，则复制示例配置
# 使用 shell 脚本实现条件复制
RUN if [ ! -f /run/application.properties ]; then \
      cp /build/src/main/resources/application.properties_example /run/application.properties; \
    fi

# 设置共享目录的挂载点
VOLUME /shared

# 设置容器启动命令
ENTRYPOINT ["java", "-jar", "/run/app.jar", "--spring.config.location=/run/application.properties"]

# 暴露应用程序的端口
EXPOSE 8080
