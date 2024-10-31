Installiert:
.zshrc
add Path for mongodb
export PATH=$PATH:/Users/vantinhchu/Documents/mongodb-macos-x86_64-8.0.1/bin


start mongodb database
cd /Users/vantinhchu/Documents/mongodb-macos-x86_64-8.0.1
sudo ./mongod --dbpath /Users/vantinhchu/Documents/mongodb-macos-x86_64-8.0.1/data/db

Use Mongdodb compass to see the database
