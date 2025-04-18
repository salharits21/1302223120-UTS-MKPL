package lib;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

public class Employee {

	private static final int[] GRADE_SALARIES = { 3000000, 5000000, 7000000 }; // Konstanta untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per bulan, grade 3: 7.000.000 per bulan)
	private String employeeId;
	private String firstName;
	private String lastName;
	private String idNumber;
	private String address;
	
	private LocalDate joinDate; //Menyederhanakan penggunaan tanggal
	
	private boolean isForeigner;
	private boolean gender; //true = Laki-laki, false = Perempuan
	
	private int monthlySalary;
	private int otherMonthlyIncome;
	private int annualDeductible;
	
	private String spouseName;
	private String spouseIdNumber;

	private List<String> childNames;
	private List<String> childIdNumbers;
	
	public Employee(String employeeId, String firstName, String lastName, String idNumber, String address, int yearJoined, int monthJoined, int dayJoined, boolean isForeigner, boolean gender) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.idNumber = idNumber;
		this.address = address;
		this.joinDate = LocalDate.of(yearJoined, monthJoined, dayJoined);
		this.isForeigner = isForeigner;
		this.gender = gender;
		
		childNames = new LinkedList<String>();
		childIdNumbers = new LinkedList<String>();
	}
	
	/**
	 * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per bulan, grade 3: 7.000.000 per bulan)
	 * Jika pegawai adalah warga negara asing gaji bulanan diperbesar sebanyak 50%
	 */
	
	public void setMonthlySalary(int grade) {
		if (grade < 1 || grade > GRADE_SALARIES.length) {
			throw new IllegalArgumentException("Invalid grade");
		}
		int baseSalary = GRADE_SALARIES[grade - 1];
		this.monthlySalary = isForeigner ? (int) (baseSalary * 1.5) : baseSalary;
	}
	
	public void setAnnualDeductible(int deductible) {	
		this.annualDeductible = deductible;
	}
	
	public void setAdditionalIncome(int income) {	
		this.otherMonthlyIncome = income;
	}
	
	public void setSpouse(String spouseName, String spouseIdNumber) {
		this.spouseName = spouseName;
		this.spouseIdNumber = spouseIdNumber; // Fixed bug here
	}
	
	public void addChild(String childName, String childIdNumber) {
		childNames.add(childName);
		childIdNumbers.add(childIdNumber);
	}
	
	private int calculateMonthsWorkedThisYear() {
		LocalDate currentDate = LocalDate.now();
		if (currentDate.getYear() == joinDate.getYear()) {
			return Math.max(0, currentDate.getMonthValue() - joinDate.getMonthValue());
		}
		return 12;
	}

	public int getAnnualIncomeTax() {
		int monthsWorked = calculateMonthsWorkedThisYear();
		int annualIncome = (monthlySalary + otherMonthlyIncome) * monthsWorked;
		boolean isMarried = spouseIdNumber != null && !spouseIdNumber.isEmpty();
		int numberOfChildren = childIdNumbers.size();

		return TaxFunction.calculateTax(annualIncome, annualDeductible, isMarried, numberOfChildren);
	}
}
