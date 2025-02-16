plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("jvm") version "1.9.0"
}

group = "com.boomzin"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	create("jooqGenerator")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.springframework.boot:spring-boot-starter-validation")

	compileOnly("org.projectlombok:lombok:1.18.36")
	implementation("joda-time:joda-time:2.13.1")
	annotationProcessor ("org.projectlombok:lombok")
	"jooqGenerator"("org.jooq:jooq-codegen:${project.property("jooqVersion")}")
	"jooqGenerator"("org.postgresql:postgresql:42.7.2")
	implementation ("org.jooq:jooq:${project.property("jooqVersion")}")
	implementation ("org.jooq:jooq-meta:${project.property("jooqVersion")}")
	implementation ("org.jooq:jooq-codegen:${project.property("jooqVersion")}")
	runtimeOnly("org.postgresql:postgresql")


	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register<JavaExec>("generateJooq") {
	group = "jooq"
	description = "Generate JOOQ code from XML configuration"
	mainClass.set("org.jooq.codegen.GenerationTool")
	classpath = configurations["jooqGenerator"]
	args = listOf("${projectDir}/src/main/resources/jooq-config.xml")
}


tasks.withType<JavaCompile> {
	options.compilerArgs.add("-Xlint:deprecation")
}