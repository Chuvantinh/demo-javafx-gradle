Installiert:
.zshrc
add Path for mongodb
export PATH=$PATH:/Users/vantinhchu/Documents/mongodb-macos-x86_64-8.0.1/bin


start mongodb database
cd /Users/vantinhchu/Documents/mongodb-macos-x86_64-8.0.1
sudo ./mongod --dbpath /Users/vantinhchu/Documents/mongodb-macos-x86_64-8.0.1/data/db

Use Mongdodb compass to see the database


export jar file
java --module-path /Library/Java/JavaVirtualMachines/jdk-23.jdk/Contents/Home/bin/java --add-modules javafx.controls,javafx.fxml -jar build/libs/orders-javafx-gradle-1.0-SNAPSHOT.jar


REGEL FOR variable and functions

Final: VARIABLE_BIG
normal variable : orderID, orderNumber, orderName
function: public void setupColumn()
variable: totalPrice, isActive, customerAddress, userCount
Good Method Name: calculateTotal()
constant in Uppercase: MAX_CONNECTIONS, DEFAULT_TIMEOUT