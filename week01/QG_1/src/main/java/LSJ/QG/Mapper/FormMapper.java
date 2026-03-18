package LSJ.QG.Mapper;

import LSJ.QG.Pojo.Form;

import java.util.List;

public interface FormMapper {
    List<Form> SelectAll(String userid);
    List<Form> SelectByStatus();
    Form SelectById(Integer id);
    void InsertForm(Form form);
    void UpdateForm(Form form);
    void DeleteForm(Integer id);
}
