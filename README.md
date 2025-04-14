# sakura_anime_video_converter

**If you are Looking for English ver. [Click here](./README_EN.md)**

来自 sakura_anime_backend 的独立视频编码器，编码为 m3u8 格式

## 运行需求/Requirements

- Java 17，

- Maven
* 已经配置好的ffmpeg，可以到[这里下载](https://www.ffmpeg.org/download.html)

#### 注意：

**application.properties现在不建议受Git更新，避免由于开发组成员更新导致的启动问题。取而代之的是同目录下的** `application.properties模板.temp` **文件，它会提供一个很好的模板，下载之后请根据它来配置你的properties文件。**

为了确保你的git会排除掉这个配置文件，请你先在克隆/下载项目的目录下，执行一行Git命令，确保排除它。

```bash
git update-index --assume-unchanged src/main/resources/application.properties
```

如果有需要确实要更新它，那么请替换为以下参数

```bash
git update-index --no-assume-unchanged
```

**Tips:**

1. 如果你打开你的IDE，发现代码或者配置存在乱码，请把工作区的文件编码都改成UTF-8，有的环境默认是ISO-8859-1或其他不正确的编码导致显示/保存错误。请在工作前务必修改为正确的UTF-8，避免后续麻烦。

2. 请记得配置上述排除application.properties文件的Git追踪。

3. 由于项目涉及视频编解码，建议在拥有NVIDIA GPU或者强大算力CPU的计算机运行。

4. 使用Postman或新增样例请使用9.31.28版本，没有可以在[这里下载](https://github.com/Radium-bit/postman_noLogin_backup/releases/tag/9.31.28)。

## 编译为Jar运行：

1. 配置OpenJDK，版本为 17(LTS)

2. 安装并配置Apache Maven 3.9.6

3. 使用IDE打开项目，建议使用`IntelliJ IDEA 2024`（建议）

4. 安装FFMPEG，并配置环境变量

5. 根据模板（.properties_example文件），配置`application.properties`

6. 定位到项目根目录，运行命令`mvn clean package`

7. 如命令正常运行，则打开项目下的`./target`子目录

8. 提取其中编译好的的`VideoConverter-0.0.1-SNAPSHOT.jar`文件和配置好的`application.properties`到合适位置

9. 执行命令
   
   ```shell
   java -jar 应用程序.jar --spring.config.location=file:/配置文件完整路径
   ```
   
   以下是一个例子：
   
   ```shell
   java -jar ./target/VideoConverter-0.0.1-SNAPSHOT.jar --spring.config.location=file:/D:/Sakura_Anime/application.properties
   ```

10. 如运行成功，通过配置好的端口即可访问数据。
