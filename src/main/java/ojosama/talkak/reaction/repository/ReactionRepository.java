package ojosama.talkak.reaction.repository;

import ojosama.talkak.reaction.model.Reaction;
import ojosama.talkak.reaction.model.ReactionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, ReactionId> {

}
