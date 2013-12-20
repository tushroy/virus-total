This is a simple to use Java API which wraps around the VirusTotal search functionality [https://www.virustotal.com/#search](https://www.virustotal.com/#search). A common use of this API is to scan files (without uploading any file content) and receive anti-virus reports.

Using the API to scan a file based on a SHA
==================================
In the following example we retrieve a SHA256 value of a file. This is value can be passed into the static method, scan, exposed from the VirusTotal class. The return value of this being a list of Report objects that contain the scan details from the various AV vendors used at VirusTotal.

	package avscan;

	import java.io.File;
	import java.io.IOException;
	import java.util.Set;
	import org.apache.commons.codec.digest.DigestUtils;
	import org.apache.commons.io.FileUtils;

	public class AVscan {

		public static void main(String[] args) throws IOException {
	  
			if (args.length == 0) {
				System.out.println("-= AV scanner - wrap around VirusTotal =-");
				System.out.println("Author: Adam Boulton");
				System.out.println("[*] Usage: java -jar AVScan <filename>");
				System.exit(0);
			}

			File file = new File(args[0]);
			System.out.println("[*] Scanning file: " + file);

			byte[] data = FileUtils.readFileToByteArray(file);

			String sha256Hex = DigestUtils.sha256Hex(data);

			//The VirusTotal class allows you to search for results using various hashes
			//and details as supported on the VirusTotal search functionality.
			Set<Report> reports = VirusTotal.scan(sha256Hex);

			//Output the details of each scan result from a vendor
			for (Report report : reports) {
				System.out.println(report.getVendor() + " - " + report.getMalwarename());
			}
		}
	}

Executing the above would result in the following output (when scanning eicar)

	[*] Scanning file: c:/data/eicar.txt
	AhnLab-V3 - 
	eSafe - EICAR Test File
	DrWeb - EICAR Test File (NOT a Virus!)
	Symantec - EICAR Test String
	F-Secure - 
	Fortinet - EICAR_TEST_FILE
	VIPRE - EICAR (v)
	eTrust-Vet - the EICAR test string
	ViRobot - 
	Microsoft - Virus:DOS/EICAR_Test_File  Avast - EICAR Test-NOT virus!!!
	F-Prot - 
	PCTools - 
	TrendMicro-HouseCall - Eicar_test_file
	CAT-QuickHeal - EICAR Test File
	Sophos - EICAR-AV-Test
	VBA32 - EICAR-Test-File
	nProtect - Trojan.Script.2270
	ClamAV - Eicar-Test-Signature
	SUPERAntiSpyware - 
	Jiangmin - EICAR-Test-File
	Avast5 - EICAR Test-NOT virus!!!
	NOD32 - Eicar test file
	Norman - 
	AntiVir - Eicar-Test-Signature
	Kaspersky - EICAR-Test-File
	K7AntiVirus - 
	Antiy-AVL - 
	VirusBuster - EICAR_test_file
	Rising - EICAR-Test-File
	Ikarus - EICAR-ANTIVIRUS-TESTFILE
	McAfee - EICAR test file
	TrendMicro - Eicar_test_file
	Comodo - 
	Panda - EICAR-AV-TEST-FILE
	McAfee-GW-Edition - EICAR test file
	AVG - EICAR_Test
	BitDefender - EICAR-Test-File (not a virus)
	GData - EICAR-Test-File
	Prevx - 
	TheHacker 


Dependencies
-----
[jsoup](http://jsoup.org/)

forked from: code.google.com/p/virus-total
