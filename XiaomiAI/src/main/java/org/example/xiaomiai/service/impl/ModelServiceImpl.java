package org.example.xiaomiai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.example.xiaomiai.entity.Model;
import org.example.xiaomiai.mapper.ModelMapper;
import org.example.xiaomiai.service.ModelService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {

    @Resource
    private ModelMapper modelMapper;

    @Override
    @Cacheable(value = "modelCache", key = "'allModelNames'")
    public List<Model> selectAllModel() {
        List<Model> res;
        QueryWrapper<Model> wrapper = new QueryWrapper<>();
        wrapper.select("DISTINCT model_name");
        res = modelMapper.selectList(wrapper);
        return res;
    }

    @Override
    @Cacheable(value = "modelCache", key = "#modelName")
    public List<Model> selectModelByModelName(String modelName) {
        List<Model> res;
        QueryWrapper<Model> wrapper = new QueryWrapper<>();
        wrapper.eq("model_name", modelName);
        res = modelMapper.selectList(wrapper);
        return res;
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "modelCache", key = "'allModelNames'"),
                    @CacheEvict(value = "modelCache", key = "#model.modelName")
            }
    )
    public Model addModel(Model model) {
        model.setId(null);
        modelMapper.insert(model);
        return model;
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "modelCache", key = "#model.modelName"),
                    @CacheEvict(value = "modelCache", key = "'allModelNames'")
            }
    )
    public int deleteModelById(int id) {
        Model model = new Model();
        model.setId(id);
        if(model.getId() == null){
            return 0;
        }
        int x = modelMapper.deleteById(model);
        return x;
    }

}
