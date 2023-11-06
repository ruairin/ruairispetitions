FROM tomcat:latest

# Copy war to tomcat app directory
# the file must be named ROOT.war if you dont want the URL
# to include the war filename
COPY target/ruairispetitions.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]