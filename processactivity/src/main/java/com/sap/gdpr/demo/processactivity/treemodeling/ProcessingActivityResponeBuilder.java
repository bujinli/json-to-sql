package com.sap.gdpr.demo.processactivity.treemodeling;

import java.util.ArrayList;
import java.util.List;

import com.sap.gdpr.demo.processactivity.treemodeling.fieldvalue.IFieldValue;
import com.sap.gdpr.demo.processactivity.treemodeling.fieldvalue.LongFieldValue;
import com.sap.gdpr.demo.processactivity.treemodeling.fieldvalue.StringFieldValue;

public class ProcessingActivityResponeBuilder {

	public static ProcessingActRoot build() {
		ProcessingActRoot demo = new ProcessingActRoot();

		demo.setActivityName("demo activity");

		TreeNode dataController = new TreeNode();
		dataController.setFieldName("data_controller");
		List<StringFieldValue> dataControllerValueList = new ArrayList<>();
		StringFieldValue f1 = new StringFieldValue("jeff.li01@sap.com");
		StringFieldValue f2 = new StringFieldValue("020202@sap.com");
		dataControllerValueList.add(f1);
		dataControllerValueList.add(f2);
		
		dataController.addValue(f1);
		dataController.addValue(f2);

		TreeNode ddNode = new TreeNode();
		ddNode.setFieldName("dd");
		// List<StringFieldValue> ddValueList = new ArrayList<>();
		// StringFieldValue ddf1 = new StringFieldValue("23");
		// ddValueList.add(ddf1);
		// ddNode.setValueList(ddValueList);
		ddNode.addValue(new StringFieldValue("23"));

		TreeNode ddNode2 = new TreeNode();
		ddNode2.setFieldName("ddNode2");
		ddNode2.addValue(new StringFieldValue("ddNode2 value"));

		ddNode.putSubNode(ddNode2.getFieldName(), ddNode2);

		dataController.putSubNode("dd", ddNode);

		demo.getContent().put("data_controller", dataController);

		// =======================================================
		TreeNode dataVolumn = new TreeNode();
		dataVolumn.setFieldName("data_volumn");

		TreeNode size = new TreeNode();
		size.setFieldName("size");
		size.addValue(new StringFieldValue("asdf"));
		TreeNode location = new TreeNode();
		location.setFieldName("location");
		location.addValue(new StringFieldValue("location"));

		dataVolumn.putSubNode("size", size);
		dataVolumn.putSubNode("location", location);

		demo.getContent().put("data_volumn", dataVolumn);

		// =======================================================
		TreeNode evaluation = new TreeNode();
		evaluation.setFieldName("evaluation");
		evaluation.addValue(new StringFieldValue("yes"));
		// List<StringFieldValue> evaluationVList = new ArrayList<>();
		// evaluationVList.add(v);
		// evaluation.setValueList(evaluationVList);

		demo.getContent().put("evaluation", evaluation);

		// =======================================================
		TreeNode rating = new TreeNode();
		rating.setFieldName("rating");
		// List<LongFieldValue> ratingVList = new ArrayList<>();
		rating.addValue(new LongFieldValue(5L));
		// v2.setFieldData(5L);
		// ratingVList.add(v2);
		// rating.setValueList(ratingVList);

		demo.getContent().put("rating", rating);

		// =======================================================
		TreeNode test = new TreeNode();
		test.setFieldName("test");
		test.addValue(new LongFieldValue(10L));
		test.addValue(new StringFieldValue("string fe"));

		demo.getContent().put("test", test);

		return demo;
	}

	// public static ProcessingActRoot build() {
	// ProcessingActRoot demo = new ProcessingActRoot();
	//
	// demo.setActivityName("demo activity");
	//
	// TreeNode<StringFieldValue> dataController = new TreeNode<>();
	// dataController.setFieldName("data_controller");
	// List<StringFieldValue> dataControllerValueList = new ArrayList<>();
	// StringFieldValue f1 = new StringFieldValue("jeff.li01@sap.com");
	// StringFieldValue f2 = new StringFieldValue("020202@sap.com");
	// dataControllerValueList.add(f1);
	// dataControllerValueList.add(f2);
	//
	// TreeNode<StringFieldValue> ddNode = new TreeNode<>();
	// ddNode.setFieldName("dd");
	// // List<StringFieldValue> ddValueList = new ArrayList<>();
	// // StringFieldValue ddf1 = new StringFieldValue("23");
	// // ddValueList.add(ddf1);
	// // ddNode.setValueList(ddValueList);
	// ddNode.addValue(new StringFieldValue("23"));
	//
	// TreeNode<StringFieldValue> ddNode2 = new TreeNode<>();
	// ddNode2.setFieldName("ddNode2");
	// ddNode2.addValue(new StringFieldValue("ddNode2 value"));
	//
	// ddNode.putSubNode(ddNode2.getFieldName(), ddNode2);
	//
	// dataController.putSubNode("dd", ddNode);
	//
	// demo.getContent().put("data_controller", dataController);
	//
	// // =======================================================
	// TreeNode<StringFieldValue> dataVolumn = new TreeNode<>();
	// dataVolumn.setFieldName("data_volumn");
	//
	// TreeNode<StringFieldValue> size = new TreeNode<>();
	// size.setFieldName("size");
	// size.addValue(new StringFieldValue("asdf"));
	// TreeNode<StringFieldValue> location = new TreeNode<>();
	// location.setFieldName("location");
	// location.addValue(new StringFieldValue("location"));
	//
	// dataVolumn.putSubNode("size", size);
	// dataVolumn.putSubNode("location", location);
	//
	// demo.getContent().put("data_volumn", dataVolumn);
	//
	// // =======================================================
	// TreeNode<StringFieldValue> evaluation = new TreeNode<>();
	// evaluation.setFieldName("evaluation");
	// List<StringFieldValue> evaluationVList = new ArrayList<>();
	// StringFieldValue v = new StringFieldValue();
	// v.setFieldData("yes");
	// evaluationVList.add(v);
	// evaluation.setValueList(evaluationVList);
	//
	// demo.getContent().put("evaluation", evaluation);
	//
	// // =======================================================
	// TreeNode<LongFieldValue> rating = new TreeNode<>();
	// rating.setFieldName("rating");
	// List<LongFieldValue> ratingVList = new ArrayList<>();
	// LongFieldValue v2 = new LongFieldValue();
	// v2.setFieldData(5L);
	// ratingVList.add(v2);
	// rating.setValueList(ratingVList);
	//
	// demo.getContent().put("rating", rating);
	//
	// // =======================================================
	// TreeNode<IFieldValue> test = new TreeNode<>();
	// test.setFieldName("test");
	// test.addValue(new LongFieldValue(10L));
	// test.addValue(new StringFieldValue("string fe"));
	//
	// demo.getContent().put("test", test);
	//
	// return demo;
	// }

}
