package gsim.blocks.source

import gsim.core.blocks.SourceBlock
import gsim.core.blocks.variables.VariableType
import gsim.core.blocks.variables.OutputVariable
import gsim.core.blocks.BlockParameter
import gsim.core.blocks.BlockParameterType
import gsim.core.blocks.variables.Variable

/**
 *
 * @author Richard Mihalovič
 */
class Constant extends SourceBlock {
	Constant() {
		setBlockName('Constant')
		blockParameters.put('constant', 1.0)
	}
	
	java.util.List<OutputVariable> getAvailableOutputs() {
		return [
			new OutputVariable(
				name: 'out(1)',
				type: VariableType.NUMBER
			)
		]
	}

	def setValue(BigDecimal value) {
		throw new Exception('Operationnot supported!')
	}

	def getInput(Integer atPosition) {
		throw new Exception('Operationnot supported!')
	}

	def getOutput(Integer atPosition) {
		return new Variable(
			type: VariableType.NUMBER,
			value: blockParameters['constant']
		)
	}

    def simulationStep(inputs, gsim.SimulationParameters parameters) {
        return null;
    }

	def setParameters(parameters) {
//		if(parameters.size() > 0)
//			value = parameters.calculatedValue ? parameters.calculatedValue : 1.0
		parameters.each { key, value ->
			if(key == 'value')
				this.blockParameters['constant'] = value
			else
				throw new IllegalArgumentException()
		}
	}

	List getAvailableParameters() {
		return [
			new BlockParameter(
				name: 'constant',
				validator: BlockParameterType.Decimal(),
				isRequired: true
			)
		]
	}
}