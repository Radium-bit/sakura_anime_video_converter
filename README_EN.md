# sakura_anime_video_converter

A standalone video encoder from **sakura_anime_backend**, encoding videos into m3u8 format.

## Requirements

- **Java 17**

- **Maven**

- **FFmpeg**: Make sure it is properly installed and configured. You can download it from [here](https://www.ffmpeg.org/download.html).

- 

#### Note:

It is now recommended that the `application.properties` file is not updated through Git to avoid startup issues caused by changes from different development team members. Instead, please use the **`application.properties模板.temp`** file located in the same directory. This file will serve as an excellent template—after downloading it, please configure your properties file accordingly.

To ensure that your Git excludes this configuration file, run the following command in the project's cloned/downloaded directory:

```bash
git update-index --assume-unchanged src/main/resources/application.properties
```

If you need to update it in the future, then run the command below to revert the exclusion:

```bash
git update-index --no-assume-unchanged
```

**Tips:**

1. If you open your IDE and notice garbled text in the code or configuration, change your workspace file encoding to **UTF-8**. Some environments may default to ISO-8859-1 or another incorrect encoding, which leads to display/storage issues. Ensure your files are in UTF-8 to avoid further complications.

2. Remember to configure Git to exclude tracking of the `application.properties` file as mentioned above.

3. Since the project involves video encoding and decoding, it is recommended to run it on a machine with an NVIDIA GPU or a powerful CPU.

4. For Postman or additional examples, please use version **9.31.28**. If you don’t have it, you can download it [here](https://github.com/Radium-bit/postman_noLogin_backup/releases/tag/9.31.28).

## Building the Jar for Execution

1. Set up **OpenJDK 17 (LTS)**.

2. Install and configure **Apache Maven 3.9.6**.

3. Open the project with an IDE; **IntelliJ IDEA 2024** is recommended.

4. Install FFmpeg and configure your system environment variables.

5. Configure `application.properties` according to the template (the `.properties_example` file).

6. Navigate to the project root directory and run the following command:
   
   ```bash
   mvn clean package
   ```

7. If the command completes successfully, go to the `./target` subdirectory in the project.

8. Extract the compiled `VideoConverter-0.0.1-SNAPSHOT.jar` and the configured `application.properties` file to a suitable location.

9. Execute the following command:
   
   ```shell
   java -jar your_application.jar --spring.config.location=file:/full/path/to/your/application.properties
   ```
   
   For example:
   
   ```shell
   java -jar ./target/VideoConverter-0.0.1-SNAPSHOT.jar --spring.config.location=file:/D:/Sakura_Anime/application.properties
   ```

10. If the application runs successfully, you can access the data via the configured port.



## Run with Docker:

1. Install Docker

2. Modify the global variables of `start-container.sh`

3. Keep the network open and run `start-container.sh`
