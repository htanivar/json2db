package sin.bse.json2db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sin.bse.json2db.model.ScripStaging;

@Repository
public interface IScripStaging extends JpaRepository<ScripStaging,Long> {

}
