package com.askit.annotationProcessor.oDataMapperGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Modifier;

import com.askit.oDataMethods.MethodCaller;
import com.askit.oDataMethods.ODataMethodManager;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

public class ODataMapperGenerator {

	// TODO map muss eindeutig sein
	// TODO exception handling

	private final List<ODataLinkHolder> annotations;
	private final ParameterSpec urlParameterGenericMapType;
	private final static String CLASS_NAME = "ODataMethodMapper";
	private final static String PACKAGE_NAME = "com.askit.oDataMethods";
	private final static String FIELD_MAP_NAME = "map";
	private final static String FIELD_ODATA_METHOD_CLASS_NAME = "odataMethods";
	private final static String PARAMETER_ODATA_STRING = "odata";
	private final static String EX_METHOD_CALL_INTERFACE_VOID_NAME = "call";
	private static final String MERHOD_RUN_METHOD_WITH_RESULT = "runMethodWithResult";
	private static final String PARAMETER_URL_PARAMETER_MAP = "urlParameters";

	public ODataMapperGenerator(final List<ODataLinkHolder> annotations) {
		this.annotations = annotations;
		final ParameterizedTypeName mapURLTypeGenerics = ParameterizedTypeName.get(Map.class, String.class, String[].class);
		urlParameterGenericMapType = ParameterSpec.builder(mapURLTypeGenerics, PARAMETER_URL_PARAMETER_MAP, Modifier.FINAL).build();
	}

	public void generate() throws IOException {

		// Fields
		final ParameterizedTypeName mapTypeGenerics = ParameterizedTypeName.get(Map.class, String.class, MethodCaller.class);
		final FieldSpec mapField = FieldSpec.builder(mapTypeGenerics, FIELD_MAP_NAME).addModifiers(Modifier.PRIVATE, Modifier.FINAL).build();
		final FieldSpec frontEndMethodsField = FieldSpec.builder(ODataMethodManager.class, FIELD_ODATA_METHOD_CLASS_NAME)
				.addModifiers(Modifier.PRIVATE, Modifier.FINAL).build();

		// Construcor
		final Builder constructorBuilder = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC)
				.addStatement("this.$N = new $T<$T,$T>()", FIELD_MAP_NAME, HashMap.class, String.class, MethodCaller.class)
				.addStatement("this.$N = new $T()", FIELD_ODATA_METHOD_CLASS_NAME, ODataMethodManager.class);

		for (int i = 0; i < annotations.size(); i++) {
			constructorBuilder.addStatement("this.$N.put($S,$L)", FIELD_MAP_NAME, annotations.get(i).getOData(),
					buildAnonymousInnerClass(annotations.get(i)));
		}

		final MethodSpec constructorMethod = constructorBuilder.build();

		// Get Method
		final MethodSpec getMethodWithResult = MethodSpec
				.methodBuilder(MERHOD_RUN_METHOD_WITH_RESULT)
				.addModifiers(Modifier.PUBLIC)
				.returns(Object.class)
				.addParameter(String.class, PARAMETER_ODATA_STRING, Modifier.FINAL)
				.addParameter(urlParameterGenericMapType)
				.addStatement("return $N.get($N).$N($N)", FIELD_MAP_NAME, PARAMETER_ODATA_STRING, EX_METHOD_CALL_INTERFACE_VOID_NAME,
						PARAMETER_URL_PARAMETER_MAP).build();

		final MethodSpec getMethodWithoutResult = MethodSpec
				.methodBuilder("runMethodWithoutResult")
				.addModifiers(Modifier.PUBLIC)
				.returns(void.class)
				.addParameter(String.class, PARAMETER_ODATA_STRING, Modifier.FINAL)
				.addParameter(urlParameterGenericMapType)
				.addStatement("$N.get($N).$N($N)", FIELD_MAP_NAME, PARAMETER_ODATA_STRING, EX_METHOD_CALL_INTERFACE_VOID_NAME,
						PARAMETER_URL_PARAMETER_MAP).build();

		// Class
		final TypeSpec mapperClass = TypeSpec.classBuilder(CLASS_NAME).addModifiers(Modifier.PUBLIC, Modifier.FINAL).addMethod(constructorMethod)
				.addField(mapField).addField(frontEndMethodsField).addMethod(getMethodWithResult).addMethod(getMethodWithoutResult).build();

		// java File
		final JavaFile javaFile = JavaFile.builder(PACKAGE_NAME, mapperClass).build();

		javaFile.writeTo(System.out);

	}

	private TypeSpec buildAnonymousInnerClass(final ODataLinkHolder annotaion) {
		final TypeSpec innerAnonInterface = TypeSpec
				.anonymousClassBuilder("")
				.addSuperinterface(ParameterizedTypeName.get(MethodCaller.class))
				.addMethod(
						MethodSpec
								.methodBuilder(EX_METHOD_CALL_INTERFACE_VOID_NAME)
								.addAnnotation(Override.class)
								.addModifiers(Modifier.PUBLIC)
								.addParameter(urlParameterGenericMapType)
								.returns(Object.class)
								.addStatement("return $N.$N($N)", FIELD_ODATA_METHOD_CLASS_NAME, annotaion.getMethodName(),
										PARAMETER_URL_PARAMETER_MAP).build()).build();

		return innerAnonInterface;
	}

	public static void main(final String[] args) throws IOException {
		final List<ODataLinkHolder> annotaions = new ArrayList<ODataLinkHolder>();
		annotaions.add(new ODataLinkHolder("GET/USER", "getUser", true));

		new ODataMapperGenerator(annotaions).generate();

	}
}
