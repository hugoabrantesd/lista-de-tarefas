package br.com.enums;

public enum PrioridadeEnum {

	ALTA {
		@Override
		public String getValue() {
			return "ALTA";
		}
	},
	MEDIA {
		@Override
		public String getValue() {
			return "MÉDIA";
		}
	},
	BAIXA

	{
		@Override
		public String getValue() {
			return "BAIXA";
		}
	};

	public abstract String getValue();

}
