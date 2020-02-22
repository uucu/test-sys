FROM tomcat:8

RUN rm -rf /usr/local/tomcat/webapps/ROOT

COPY ./target/*.war /usr/local/tomcat/webapps/ROOT.war

# copy arthas
COPY --from=hengyunabc/arthas:latest /opt/arthas /opt/arthas

EXPOSE 8080

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo "Asia/Shanghai" > /etc/timezone

