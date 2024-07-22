docker cp es01:/usr/share/elasticsearch/config/certs/http_ca.crt /tmp/.
cat /tmp/http_ca.crt
echo 'ssl:';
echo '  certificate_authorities:';
echo '  - |';
cat /tmp/http_ca.crt | sed 's/^/    /'
openssl x509 -fingerprint -sha256 -noout -in /tmp/http_ca.crt | awk -F"=" {' print $2 '} | sed s/://g