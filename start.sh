echo "Printing current home: ${HOME}"
APP_PATH = "${HOME}/bin/vertx-drools-request-validator"

cd $APP_PATH || fails "Unable to change directory to $APP_PATH"
APP_JAR=$(ls vertx-drools-request-validator-1.0-SNAPSHOT.jar)

JAVA_CMD="java -cp $APP_JAR:lib/* org.sanjiv.requestvalidator.StartUp"
echo "FINAL JAVA CMD: $JAVA_CMD"
$JAVA_CMD