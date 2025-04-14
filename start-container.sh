#!/bin/bash


# 提示用户检查并修改全局变量
echo "⚠️ 请在执行前检查并修改脚本的全局变量！"
read -r -p "是否确认继续执行？[Y/n] " confirm
if [[ ! "$confirm" =~ ^[Yy]$ ]]; then
  echo "操作已取消。"
  exit 0
fi

# 设置变量
IMAGE_NAME="video-converter:latest"       # Docker 镜像名称
CONTAINER_NAME="video-converter"          # 容器名称
HOST_SHARED_DIR="/absolute/path/to/shared"  # 宿主机上的共享目录路径，请根据实际情况修改
HOST_CONFIG_FILE="/absolute/path/to/config/application.properties"  # 宿主机上的配置文件路径，请根据实际情况修改
CONTAINER_SHARED_DIR="/shared"            # 容器内的共享目录路径
CONTAINER_CONFIG_FILE="/run/application.properties"  # 容器内的配置文件路径
HOST_PORT=8080                            # 宿主机暴露的端口
CONTAINER_PORT=8080                       # 容器内应用监听的端口

# 创建宿主机共享目录（如果不存在）
mkdir -p "$HOST_SHARED_DIR"

# 创建宿主机配置文件目录（如果不存在）
mkdir -p "$(dirname "$HOST_CONFIG_FILE")"

# 如果宿主机配置文件不存在，则从示例文件复制
if [ ! -f "$HOST_CONFIG_FILE" ]; then
  echo "宿主机配置文件不存在，正在从示例文件复制..."
  cp ./src/main/resources/application.properties_example "$HOST_CONFIG_FILE"
fi

# 构建 Docker 镜像
echo "正在构建 Docker 镜像..."
docker build -t "$IMAGE_NAME" .

# 检查是否存在同名容器
if [ "$(docker ps -aq -f name=^/${CONTAINER_NAME}$)" ]; then
    OLD_IMAGE_ID=$(docker inspect --format='{{.Image}}' "$CONTAINER_NAME" | cut -d':' -f2 | head -c 12)

    # 版本比对逻辑[6,7](@ref)
    if [[ "$NEW_IMAGE_ID" != "$OLD_IMAGE_ID" ]]; then
        echo "检测到镜像版本更新($OLD_IMAGE_ID → $NEW_IMAGE_ID)，自动覆盖旧容器"
        docker rm -f "$CONTAINER_NAME"
    else
        # 交互式确认逻辑[4,5](@ref)
        read -r -p "发现同名容器但版本未更新，是否覆盖？[y/N] " confirm
        if [[ "$confirm" =~ ^[Yy]$ ]]; then
            echo "正在删除旧容器..."
            docker rm -f "$CONTAINER_NAME"
        else
            echo "操作已取消"
            exit 0
        fi
    fi
fi

# 启动 Docker 容器
echo "正在启动 Docker 容器..."
docker run -d \
  --name "$CONTAINER_NAME" \
  -p "$HOST_PORT":"$CONTAINER_PORT" \
  -v "$HOST_SHARED_DIR":"$CONTAINER_SHARED_DIR" \
  -v "$HOST_CONFIG_FILE":"$CONTAINER_CONFIG_FILE" \
  "$IMAGE_NAME"

echo "容器已启动，名称：$CONTAINER_NAME，访问端口：$HOST_PORT"


