package lsj.gdut.project02.service;

import lsj.gdut.project02.pojo.Form;

import java.util.List;

public interface FormService {
    public List<Form> SelectAll();
    public List<Form> SelectAll(String userid);
    public List<Form> SelectByStatus();
    public Form SelectById(Integer id);
    public void InsertForm(String uerid,String device_type,String description,String img_url);
    public void UpdateForm(Form form);
    public void DeleteForm(Integer id);
}
