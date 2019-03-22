FROM rmpestano/wildfly:16.0.0

COPY ./docker/standalone.conf ${WILDFLY_HOME}/bin/

COPY ./target/${artifactId}.war ${DEPLOYMENT_DIR}