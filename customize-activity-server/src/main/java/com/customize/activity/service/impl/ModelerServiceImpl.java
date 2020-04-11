package com.customize.activity.service.impl;

import com.customize.activity.domain.vo.ActModelVO;
import com.customize.activity.service.ModelerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ModelerServiceImpl implements ModelerService {
    private final RepositoryService repositoryService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ModelerServiceImpl(RepositoryService repositoryService, ObjectMapper objectMapper) {
        this.repositoryService = repositoryService;
        this.objectMapper = objectMapper;
    }

    @Override
    public Model createModel(ActModelVO actModelVO) throws Exception {
        Model model = repositoryService.newModel();
        model.setKey(actModelVO.getKey());
        model.setName(actModelVO.getName());
        model.setCategory(actModelVO.getCategory());
        model.setVersion(Integer.parseInt(String.valueOf(repositoryService.createModelQuery().modelKey(model.getKey()).count() + 1)));

        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, model.getName());
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, model.getVersion());
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, actModelVO.getDescription());

        model.setMetaInfo(modelNode.toString());
        repositoryService.saveModel(model);

        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");

        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.set("stencilset", stencilSetNode);

        repositoryService.addModelEditorSource(model.getId(), editorNode.toString().getBytes("utf-8"));
        return model;
    }

    @Override
    public Model getModelById(String modelId) {
        return repositoryService.getModel(modelId);
    }

    @Override
    public Page<Model> getAllModels(Pageable pageable) {
        ModelQuery modelQuery = repositoryService.createModelQuery().latestVersion().orderByLastUpdateTime().desc();
        return new PageImpl<>(modelQuery.listPage(pageable.getPageNumber() * pageable.getPageSize(), pageable.getPageSize()), pageable, modelQuery.count());
    }

    @Override
    public List<ProcessDefinition> deployModel(String modelId) throws Exception {
        Model modelData = repositoryService.getModel(modelId);
        BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
        ObjectNode editorNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
        BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
        BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
        byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
        ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
        Deployment deployment = repositoryService.createDeployment().name(modelData.getName())
                .addInputStream(modelData.getKey() + ".bpmn20.xml", in).enableDuplicateFiltering().deploy();
        return repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
    }

    @Override
    public void deleteModel(String modelId) {
        repositoryService.deleteModel(modelId);
    }

    @Override
    public byte[] getModelXml(String modelId) throws IOException {
        Model modelData = repositoryService.getModel(modelId);
        BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
        ObjectNode editorNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
        BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
        BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
        return xmlConverter.convertToXML(bpmnModel);
    }
}
