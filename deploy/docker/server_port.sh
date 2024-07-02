NEW_PORT=8080
for ((i = 0; i < ${#OLD_PORT_LIST[@]}; i++)); do
    OLD_PORT=${OLD_PORT_LIST[i]}
    if [ $NEW_PORT -le $OLD_PORT ]; then
        NEW_PORT=$(($OLD_PORT + 1))
    fi
done

MAX_RETRIES=50

for ((CHECK_PORT=$NEW_PORT; CHECK_PORT<=$((NEW_PORT+MAX_RETRIES)); CHECK_PORT++)); do
    RUNNING=$(netstat -an | grep 'LISTEN' | grep ".$CHECK_PORT")
    if [ -z "$RUNNING" ] ; then
        echo "$CHECK_PORT"
        break
    fi
done

if [ $NEW_PORT -gt $((NEW_PORT+MAX_RETRIES)) ]; then
    echo "无法找到可用端口"
    exit -1
fi