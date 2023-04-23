from kafka import KafkaProducer
import time

producer = KafkaProducer(bootstrap_servers=['kafka:29092'])

for i in range(10):
    message = f"Message {i}"
    producer.send('test-topic', value=bytes(message, encoding='utf-8'))
    time.sleep(1)

print("All messages sent")