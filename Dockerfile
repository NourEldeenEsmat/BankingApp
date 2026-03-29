# استخدم Java image جاهزة
FROM eclipse-temurin:8u482-b08-jre-ubi10-minimal

# حدد مكان العمل داخل الكونتينر
WORKDIR /app

# انسخ ملف الـ jar
COPY target/*.jar app.jar

# شغل التطبيق
ENTRYPOINT ["java", "-jar", "app.jar"]
