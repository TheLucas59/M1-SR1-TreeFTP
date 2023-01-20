# SR1 - Tree FTP - Lucas Plé

## Compiler le projet 
```
mvn test clean compile assembly:single
```

## Générer la Javadoc
```
mvn javadoc:javadoc
```

## Lancer le projet 
```
java -jar target/TreeFTP.jar <address> [<login> <password>]
```