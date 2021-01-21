package com.budgetcars.net.test;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.bugdetcars.net.Application;
import com.bugdetcars.net.scan.Scan;


@SpringBootTest(classes = Application.class)
class ScanTests {
	
	Scan scan;
	
	@Test
	void testScanMethod() {
		scan = new Scan();
		scan.scanAll(scan.maxPageCount);
	}

}
