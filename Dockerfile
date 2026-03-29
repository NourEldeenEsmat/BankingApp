# استخدم Java image جاهزة
FROM eclipse-temurin:17-jdk

# حدد مكان العمل داخل الكونتينر
WORKDIR /app

# انسخ ملف الـ jar
COPY target/*.jar app.jar

# شغل التطبيق
ENTRYPOINT ["java", "-jar", "app.jar"]
