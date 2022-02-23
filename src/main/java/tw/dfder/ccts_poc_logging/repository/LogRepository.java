package tw.dfder.ccts_poc_logging.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tw.dfder.ccts_poc_logging.Entity.LogMessageEnvelope;

@Repository
public interface LogRepository extends MongoRepository<LogMessageEnvelope,String> {
    LogMessageEnvelope findByPaymentId(String pid);

}

