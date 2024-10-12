package ojosama.talkak.viewingRecord.repository;

import ojosama.talkak.viewingRecord.domain.ViewingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewingRecordRepository extends JpaRepository<ViewingRecord, Long> {

}
