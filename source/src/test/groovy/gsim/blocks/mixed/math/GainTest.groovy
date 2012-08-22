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
class GainTest {

	@Test
	void defaultValues() {
		def b = new Gain()

		assertEquals('Gain', b.getBlockName())
        assertEquals(BlockType.MIXED, b.getBlockType())
	}

	@Test
	void setBlockValues() {
		def value = 1234567890.1234567890
		def scale = 7.7

		def b = new Gain()
		b.setParameters([scaleFactor: scale])
		
		assertEquals(b.getParameter('scaleFactor'), scale)
	}

//	@Test(expected = IllegalArgumentException.class)
//	void setInvalidInputValuePosition() {
//		def b = new Gain()
//		b.getInput(0)
//	}

	@Test(expected = IllegalArgumentException.class)
	void getInvalidOutputPosition() {
		def b = new Gain()
		b.getOutput(0)
	}

	@Test
	void calculateOutputValue() {
		def b = new Gain()
		b.setParameters([scaleFactor: 5.0])

		// 2 * 5 = 10
		def inputParameters = []
		inputParameters[1] = Variable.number(2.0)
		b.simulationStep(inputParameters, new SimulationParameters())

		assertEquals(b.getOutput(1).value, 10.0, 0.0)
		assertEquals(b.getOutput(1).type, VariableType.NUMBER)
	}
}