package lsj.gdut.project02.mapper;

import lsj.gdut.project02.pojo.Form;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FormMapper {
    List<Form> SelectAll(String userid);
    List<Form> SelectByStatus();
    Form SelectById(Integer id);
    void InsertForm(Form form);
    void UpdateForm(Form form);
    void DeleteForm(Integer id);
}
