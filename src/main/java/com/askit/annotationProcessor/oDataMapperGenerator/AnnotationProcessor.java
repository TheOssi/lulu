package com.askit.annotationProcessor.oDataMapperGenerator;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

//TODO todo

@SupportedAnnotationTypes({ "annotations.PrintMe" })
public class AnnotationProcessor extends AbstractProcessor {

	@Override
	public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment env) {
		final Messager messager = processingEnv.getMessager();
		for (final TypeElement te : annotations) {
			for (final Element e : env.getElementsAnnotatedWith(te)) {
				messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + e.toString());
			}
		}
		return true;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}
}