sudo su root

echo "sending dir"
sshpass -p Nju83269595 ssh -o StrictHostKeyChecking=no root@39.100.250.51 rm -rf /home/codeneuron/docker
sshpass -p Nju83269595 scp -r ./target/docker root@39.100.250.51:/home/codeneuron

echo "making image"
sshpass -p Nju83269595 ssh -o StrictHostKeyChecking=no root@39.100.250.51 docker build -t codeneuron:release /home/codeneuron/docker

echo "stop present container"
sshpass -p Nju83269595 ssh -o StrictHostKeyChecking=no root@39.100.250.51 chmod 777 /home/codeneuron/stop.sh
sshpass -p Nju83269595 ssh -o StrictHostKeyChecking=no root@39.100.250.51 /home/codeneuron/stop.sh

echo "runing container"
sshpass -p Nju83269595 ssh -o StrictHostKeyChecking=no root@39.100.250.51 docker run -d -p 8088:8088 --name="codeneuron" codeneuron:release

echo "sending second dir"
sshpass -p Nju83269595 ssh -o StrictHostKeyChecking=no root@39.97.211.198 rm -rf /home/codeneuron/docker
sshpass -p Nju83269595 scp -r ./target/docker root@39.97.211.198:/home/codeneuron

echo "making second image"
sshpass -p Nju83269595 ssh -o StrictHostKeyChecking=no root@39.97.211.198 docker build -t codeneuron:release /home/codeneuron/docker

echo "stop second present container"
sshpass -p Nju83269595 ssh -o StrictHostKeyChecking=no root@39.97.211.198 chmod 777 /home/codeneuron/stop.sh
sshpass -p Nju83269595 ssh -o StrictHostKeyChecking=no root@39.97.211.198 /home/codeneuron/stop.sh

echo "runing second container"
sshpass -p Nju83269595 ssh -o StrictHostKeyChecking=no root@39.97.211.198 docker run -d -p 8088:8088 --name="codeneuron" codeneuron:release