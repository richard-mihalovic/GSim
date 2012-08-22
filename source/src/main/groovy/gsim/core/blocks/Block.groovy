package gsim.core.blocks

import gsim.SimulationParameters
import gsim.core.blocks.variables.InputVariable
import gsim.core.blocks.variables.OutputVariable
import gsim.SchemaValidationError

/**
 *
 * @author Richard Mihalovič
 */
abstract class Block {
	def BigDecimal calculatedValue = 0.0
	
	String blockName = ''
	String blockId = ''
	protected def blockParameters = [:]
    
    BlockType blockType = BlockType.UNKNOWN

	abstract java.util.List<OutputVariable> getAvailableOutputs()
	abstract java.util.List<InputVariable> getAvailableInputs()
	abstract java.util.List<BlockParameter> getAvailableParameters()

	abstract def getInput(Integer atPosition) // TODO: can be removed ... ?
	abstract def getOutput(Integer atPosition)

	abstract def setParameters(parameters)

	abstract def simulationStep(inputs, SimulationParameters parameters)

	def getParameter(String name) {
		return this.blockParameters[name]
	}

	def setParameter(String key, value) {
		// TODO: validate parameter
		blockParameters.put(key, value)
	}

	def validateParameters() {
		def List<SchemaValidationError> validationErrors = []

		getAvailableParameters().each { BlockParameter parameter ->
			def parameterValue = blockParameters[parameter.name]

			if(parameterValue == null && parameter.isRequired) {
				validationErrors.add(
					new SchemaValidationError(
						blockId: this.blockId,
						errorMessage: parameter.name + ': required parameter value!'
					)
				)
			} else {
				def isValid = (parameterValue ==~ parameter.validator)
				if(!isValid) {
					validationErrors.add(
						new SchemaValidationError(
							blockId: this.blockId,
							errorMessage: this.blockId + '[' + parameter.name + ']: bad parameter value!'
						)
					)
				}
			}
		}

		return validationErrors
	}
}

