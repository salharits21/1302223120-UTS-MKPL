package lib;

public class TaxFunction {

	
	/**
	 * Fungsi untuk menghitung jumlah pajak penghasilan pegawai yang harus dibayarkan setahun.
	 * 
	 * Pajak dihitung sebagai 5% dari penghasilan bersih tahunan (gaji dan pemasukan bulanan lainnya dikalikan jumlah bulan bekerja dikurangi pemotongan) dikurangi penghasilan tidak kena pajak.
	 * 
	 * Jika pegawai belum menikah dan belum punya anak maka penghasilan tidak kena pajaknya adalah Rp 54.000.000.
	 * Jika pegawai sudah menikah maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000.
	 * Jika pegawai sudah memiliki anak maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000 per anak sampai anak ketiga.
	 * 
	 */
	private static final int BASE_NONTAXABLE_INCOME = 54000000;
	private static final int MARRIAGE_ALLOWANCE = 4500000;
	private static final int CHILD_ALLOWANCE = 4500000;
	private static final int MAX_CHILDREN = 3;
	private static final double TAX_RATE = 0.05;

	
	public static int calculateTax(int annualIncome, int deductible, boolean isMarried, int numberOfChildren) {
		if (numberOfChildren > MAX_CHILDREN) {
			numberOfChildren = MAX_CHILDREN;
		}

		int nonTaxableIncome = BASE_NONTAXABLE_INCOME;

		if (isMarried) {
			nonTaxableIncome += MARRIAGE_ALLOWANCE;
		}

		nonTaxableIncome += numberOfChildren * CHILD_ALLOWANCE;

		int taxableIncome = annualIncome - deductible - nonTaxableIncome;
		int tax = (int) Math.round(TAX_RATE * Math.max(taxableIncome, 0));

		return tax;
	}
	
}
