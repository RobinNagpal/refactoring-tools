package edu.pdx.cs.multiview.smelldetector.detectors.duplicateCode;

class ClassAndMethodName {
	String[] classAndMethodName;

	public ClassAndMethodName(String[] classAndMethodName) {
		this.classAndMethodName = classAndMethodName;
	}

	@Override
	public int hashCode() {
		return classAndMethodName[0].hashCode() + classAndMethodName[1].hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		String[] classNameAndMethodNameArg = ((ClassAndMethodName) obj).classAndMethodName;
		return classAndMethodName[0].equals(classNameAndMethodNameArg[0])
				&& classAndMethodName[1].equals(classNameAndMethodNameArg[1]);
	}

	@Override
	public String toString() {
		return "[" + classAndMethodName[0] + ":" + classAndMethodName[1] + "]";
	}
}