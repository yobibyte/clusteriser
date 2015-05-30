export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.7.0_75.jdk/Contents/Home 
#mvn3 clean install -U
mvn3 clean compile assembly:single -f ~/Documents/dev/clusteriser/pom.xml
cd ~/Documents/dev/clusteriser/target
unset JAVA_HOME
                                                                                                                                     
