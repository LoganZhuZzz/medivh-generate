package tech.medivh.generate.core.engine

import tech.medivh.generate.core.FileWriter
import tech.medivh.generate.core.SimpleFileWriter
import tech.medivh.generate.core.Template
import tech.medivh.generate.core.WriteRule
import tech.medivh.generate.core.env.GeneratorContext


/**
 * @author gxz gongxuanzhangmelt@gmail.com
 **/
object MedivhGenerator {

    lateinit var writeRule: WriteRule

    fun generate() {
        val modules = ModuleLoader.loadModules()
        modules.forEach { module ->
            val templates = module.templateProvider().getTemplates()
            val contexts = module.contextProvider().computeContext()
            templates.zip(contexts, this::merge).forEach { fileWriter ->
                fileWriter.write()
            }
        }
    }

    private fun merge(template: Template, context: GeneratorContext): FileWriter {
        return SimpleFileWriter(template, context, writeRule)
    }

}
