package org.example.xiaomiai.service;

import org.example.xiaomiai.entity.Model;

import java.util.List;

public interface ModelService {
    List<Model> selectAllModel();

    List<Model> selectModelByModelName(String modelName);

    Model addModel(Model model);

    int deleteModelById(int id);
}
