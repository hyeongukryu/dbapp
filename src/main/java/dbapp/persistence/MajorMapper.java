package dbapp.persistence;
import java.util.List;
import org.apache.ibatis.annotations.Select;
import dbapp.domain.Major;

public interface MajorMapper {
	@Select("SELECT majorId AS id, name AS name FROM major")
	public List<Major> getAll();
}
