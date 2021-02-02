#!/bin/bash
echo "Criando backup..."
hoje=$(date +"%d-%m-%y")
sudo docker exec -u projeto ProjetoBanco pg_dump -Fc dbProjeto > /home/ubuntu/backups/backup-$hoje.sql
echo "Apagando backups antigos"
find /home/ubuntu/backups -type f -mtime 7 -exec rm -r {} \;
echo "Finalizado rotina de backup!"
