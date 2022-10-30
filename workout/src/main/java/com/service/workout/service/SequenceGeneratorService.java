package com.service.workout.service;



import com.service.workout.model.DatabaseSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.jayway.jsonpath.Criteria.where;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceGeneratorService {

//    private MongoOperations mongoOperations;
//
//    @Autowired
//    public SequenceGeneratorService(MongoOperations mongoOperations) {
//        this.mongoOperations = mongoOperations;
//    }
//    public long generateSequence(String seqName) {
//
//        DatabaseSequence counter = mongoOperations.findAndModify(query((CriteriaDefinition) where("_id").is(seqName)),
//                new Update().inc("seq",1), options().returnNew(true).upsert(true),
//                DatabaseSequence.class);
//        return !Objects.isNull(counter) ? counter.getSeq() : 1;
//
//    }

    @Autowired
    private MongoOperations mongoOperations;


    public int getSequenceNumber(String sequenceName) {
        //get sequence no
        Query query = new Query(Criteria.where("id").is(sequenceName));
        //update the sequence no
        Update update = new Update().inc("seq", 1);
        //modify in document
        DatabaseSequence counter = mongoOperations
                .findAndModify(query,
                        update, options().returnNew(true).upsert(true),
                        DatabaseSequence.class);

        return !Objects.isNull(counter) ? (int) counter.getSeq() : 1;
    }
}
