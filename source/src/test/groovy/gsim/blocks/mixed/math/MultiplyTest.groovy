package gsim.blocks.mixed.math

import gsim.SimulationParameters
import gsim.core.blocks.BlockType
import org.junit.Test
import static org.junit.Assert.assertEquals
import gsim.core.blocks.variables.VariableType
import gsim.core.blocks.variables.Variable

/**
 *
 * @author Richard Mihalovič
 */
class MultiplyTest {

	@Test
	void defaultValues() {
		def b = new Multiply()

		assertEquals('Multiply', b.getBlockName())
        assertEquals(BlockType.MIXED, b.getBlockType())
		assertEquals('**', b.getParameter('operations'))
	}

	@Test
	void calculateMultiplyMultiply() {
		def b = new Multiply()

		// operations: **
		// 2 * 5 = 10
		def inputParameters = []
		inputParameters[1] = Variable.number(2.0)
		inputParameters[2] = Variable.number(5.0)
		b.simulationStep(inputParameters, new SimulationParameters())

		assertEquals(b.getOutput(1).value, 10.0, 0.0)
		assertEquals(b.getOutput(1).type, VariableType.NUMBER)
	}

	@Test
	void calculateMultiplyMultiplyMultiply() {
		def b = new Multiply()
		b.setParameters([operations: '***'])

		// operations: ***

		// 10 * 10 * 10 = 1000
		def inputParameters = []
		inputParameters[1] = Variable.number(10.0)
		inputParameters[2] = Variable.number(10.0)
		inputParameters[3] = Variable.number(10.0)
		b.simulationStep(inputParameters, new SimulationParameters())

		assertEquals(b.getOutput(1).value, 1000.0,  0.0)
		assertEquals(b.getOutput(1).type, VariableType.NUMBER)
	}

	@Test
	void calculateMultiplyDivide() {
		def b = new Multiply()
		b.setParameters([operations: '*/'])

		// operations: */

		// 20 / 10 = 2
		def inputParameters = []
		inputParameters[1] = Variable.number(20.0)
		inputParameters[2] = Variable.number(10.0)

		b.simulationStep(inputParameters, new SimulationParameters())

		assertEquals(b.getOutput(1).value, 2.0, 0.0)
		assertEquals(b.getOutput(1).type, VariableType.NUMBER)
	}

//	@Test(expected = IllegalArgumentException.class)
//	void invalidOptions() {
//		def b = new Multiply()
//		b.setParameters([operations: '*x'])
//	}
}