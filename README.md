# WARNING:

**This project contains many SECURITY FLAWS and is NOT INTENDEND FOR NORMAL USAGE.**
This serves as registration challenge for the course Hackercontest organized by usd AG.

-------------------------
-------------------------

# Issues
Issues is a simple issue tracking platform. It's built to be very customisable. Feel free to fork this project as a basis for your  own issue tracking platform.

### Built With
- Spring Boot (v2.3.3)
- Spring Data JPA
- Spring Validation
- Spring Security
- Mapstruct
- Lombok
- Swagger
- Thymeleaf
- Maven


## Getting Started
### Prerequisites
* Java 8
  ```console
  // RHEL based
  # yum install java
  // Debian based
  # apt install default-jdk 
  // Arch based
  # pacman -S jdk-openjdk    
  
  ```
* Maven
  ```console
  // RHEL based
  # yum install maven
  // Debian based
  # apt install maven 
  // Arch based
  # pacman -S maven  
  ```
* Any SQL compliant Database with a Database and User set up for this application. We recommend Postgresql or MariaDB.

### Configure the jdbc connection

Edit the "DATASOURCE" section in `src/main/resources/application.properties` to match your DB configuration [^1]. 
```markdown
# DATASOURCE
spring.datasource.url=jdbc:<DBtype>://<DBip|DBdomain>:<DBport>/<DBname>
spring.datasource.username=<DBusername>
spring.datasource.password=<DBpassword>
```
You can also use the provided docker-compose.
If you do so, you should generate a new Password for the Database and edit the `docker-compose.yml` and `src/main/resources/application.properties` files accordingly.

### Compiling

Navigate to the root of the project. To build the project via command line, run the command below:

```$ mvn clean install```

### Deploying

You can deploy the project with Java directly:

```$ java -jar issues.jar ```

If you want to run this in production, we recommend to set up a Tomcat instance and deploy the application to it using `mvn tomcat<TOMCAT_VERSION>:deploy`

[This](https://www.baeldung.com/tomcat-deploy-war) is a detailed Guide describing how to configure maven and Tomcat in this case.

### First run

Vist `localhost:8080` and log in with `admin:admin`. Change the admin password immediately.


-------------------------
-------------------------

# Reporting Bugs

When reporting a bug, make sure not to report it as an issue, but privately via the upload link you received in the challenge briefing.
Within your report, provide a quick overview of the vulnerability and provide a minimalistic and easy to understand **Proof of Concept** (PoC),
which allows us to replicate the issue step-by-step.

If the impact of the vulnerability is not obvious, briefly explain how the bug affects owners, admins or users.

Assign each vulnerability a criticality (critical, high, medium, low). To assess the criticality of bugs, you may refer to 
any CVSSv3 calculator or [Bugcrowds VRT](https://bugcrowd.com/vulnerability-rating-taxonomy).

The filename should follow the format `<matriculation number>_<submission number>.md`

You can use this template to submit reports (fill in the parts in <>):
```markdown
# Title: [<criticality>] <Type of vulnerabiliy> in <Component> (Optional: allows <impact>)

## Description
<Briefly describe the bug>

## PoC
<Provide a step-by-step replication guide, you can include screenshots>

## Impact
<Describe how this affects users and project owners. (Only required if the impact is not obvious)>

## Fix
<Briefly describe how this issue can be fixed>

<email adress> <matriculation number>
```

For example:
```markdown
$ cat 2991234_1.md
# [medium] Reflected XSS in Searchbar

## Description
Cross Site Scripting attacks (XSS) allow an attacker to inject arbitrary javascript into a client's browser.
This can be exploited in many different ways, most notably to steal a user session, initiate a CRSF attack 
or to redirect the user to another page.
To succesfully conduct a **reflected** XSS attack, the victim needs to follow a crafted link.

## PoC
1. Open a browser and go to `example.com/index.php?search="><iframe src="java%00script:alert(document.cookie)"/>//`

## Fix
Never trust user input and sanitize before serving it. With php, you 
can use the built-in function [htmlspectialchars](https://www.php.net/manual/de/function.htmlspecialchars.php).  

jane.doe@stud.tu-darmstadt.de 2991234
```

## Rules

 * Each bug must be exploitable (e.g don't report outdated software if no exploitation vector is present).
 * Create one report per vulnerability. If you need to chain vulnerabilities to achieve greater impact, you may report them as one.
 * Do not report different exploits for the same bug.
 * Any bugs that require header or request tampering (an on-path attacker) to exploit, will be disregarded.
 * Don't report "unsafe" CSP or non HTTP-only cookie policies.
 * If an exploit requires admin privileges, it will only be accepted if it affects the host.
 * When in doubt, we will set the criticality according to the calculated CVSSv3 score.

### Evaluation

Each reported and reproducible submission will be awarded points according to its criticality.
If it is __**necessary**__ to chain vulnerabilities to achive a higher criticality, the awarded points will be higher than the sum of the individual vulnerabilities.

### Questions

If you have questions about the task, you can contact us via the email address provided in the briefing. Note that we can't answer technical questions.
If you struggle with the challenge, take a look at the material in the next section.

### Helpful Material
 * [OSWAP Top 10](https://owasp.org/www-project-top-ten/)
 * [Portswigger Academy](https://portswigger.net/web-security)
 * [Hacktricks: Web Vulnerabilities Methodology](https://book.hacktricks.xyz/pentesting-web/web-vulnerabilities-methodology)
 * [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)
 * [Burp Suite Community Edition](https://portswigger.net/burp/communitydownload)
 * [Resources-for-Beginner-Bug-Bounty-Hunters](https://github.com/nahamsec/Resources-for-Beginner-Bug-Bounty-Hunters)

 ### References
 [^1]: [Spring: Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
