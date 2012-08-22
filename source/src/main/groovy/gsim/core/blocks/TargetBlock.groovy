package gsim.core.blocks

import gsim.SimulationParameters
import gsim.core.blocks.variables.OutputVariable

/**
 *
 * @author Richard Mihalovič
 */
abstract class TargetBlock extends Block {
    def TargetBlock() {
        this.blockType = BlockType.TARGET    
    }

	java.util.List<OutputVariable> getAvailableOutputs() {
		return []
	}

	def simulationStep(SimulationParameters parameters) {
	}
}

