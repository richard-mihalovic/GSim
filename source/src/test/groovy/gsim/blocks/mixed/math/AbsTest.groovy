package gsim.blocks.mixed.math

import gsim.SimulationParameters
import gsim.core.blocks.BlockType
import org.junit.Test
import static org.junit.Assert.assertEquals
import gsim.core.blocks.variables.Variable
import gsim.core.blocks.variables.VariableType

/**
 *
 * @author Richard Mihalovič
 */
class AbsTest {

	@Test
	void defaultValues() {
		def b = new Abs()

		assertEquals('Abs', b.getBlockName())
        assertEquals(BlockType.MIXED, b.getBlockType())
	}

	@Test
	void calculateOutputValue() {
		def b = new Abs()

		def inputParameters = []
		inputParameters[1] = Variable.number(1.0)
		
		b.simulationStep(inputParameters, new SimulationParameters())

		def output = b.getOutput(1)
		assertEquals(output.value, 1.0, 0.0)
		assertEquals(output.type, VariableType.NUMBER)

		// abs (-1) = 1
		inputParameters[1] = new Variable().number(-1.0)
		b.simulationStep(inputParameters, new SimulationParameters())

		assertEquals(output.value, 1.0, 0.0)
		assertEquals(output.type, VariableType.NUMBER)
	}
}