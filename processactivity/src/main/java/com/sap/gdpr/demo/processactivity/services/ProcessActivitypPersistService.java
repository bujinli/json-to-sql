package com.sap.gdpr.demo.processactivity.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sap.gdpr.demo.processactivity.models.FieldEntity;
import com.sap.gdpr.demo.processactivity.models.NodeEntity;
import com.sap.gdpr.demo.processactivity.models.ProcessActivityEntity;
import com.sap.gdpr.demo.processactivity.treemodeling.ProcessingActRoot;
import com.sap.gdpr.demo.processactivity.treemodeling.TreeNode;
import com.sap.gdpr.demo.processactivity.treemodeling.fieldvalue.IFieldValue;

public class ProcessActivitypPersistService {
	

	public static void saveProcessActivity(ProcessingActRoot actTree, List<NodeEntity> retNodeList,
			List<FieldEntity> retFieldList) {

		ProcessActivityEntity paEntity = new ProcessActivityEntity();
		paEntity.setKey(actTree.getId());
		paEntity.setName(actTree.getActivityName());

		transfer(actTree.getId(), null, actTree.getContent(), retNodeList, retFieldList);

	}

	public static void transfer(String processActivityKey, String parentId, Map<String, TreeNode> map,
			List<NodeEntity> retNodeList, List<FieldEntity> retFieldList) {

		for (Map.Entry<String, TreeNode> entry : map.entrySet()) {

			NodeEntity node = new NodeEntity();
			node.setProcessActivityKey(processActivityKey);
			node.setNodeKey(entry.getValue().getId());
			node.setParentNodeKey(parentId);
			retNodeList.add(node);

			if (entry.getValue().getValueList() == null) {
				FieldEntity field = new FieldEntity(UUID.randomUUID().toString(), node.getNodeKey(), entry.getKey(),
						null, null);
				// field.setFieldKey(UUID.randomUUID().toString()); // uuid
				// field.setNodeKey(node.getNodeKey());
				// field.setFieldName(entry.getKey());
				// field.setFieldValue(null);
				retFieldList.add(field);
			} else {
				for (IFieldValue temp : entry.getValue().getValueList()) {
					FieldEntity field = new FieldEntity(UUID.randomUUID().toString(), node.getNodeKey(), entry.getKey(),
							temp.getValue(), temp.getConcreteClass().toString());
					// FieldEntity field = new FieldEntity();
					// field.setFieldKey(UUID.randomUUID().toString()); // uuid
					// field.setNodeKey(node.getNodeKey());
					// field.setFieldName(entry.getKey());
					// field.setFieldValue(temp.getValue());
					retFieldList.add(field);
				}
			}

			if (entry.getValue().getSubNode() != null && !entry.getValue().getSubNode().isEmpty()) {
				transfer(processActivityKey, node.getNodeKey(), entry.getValue().getSubNode(), retNodeList,
						retFieldList);
			}
		}

	}

}
