package org.woodwhale.poi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.woodwhale.poi.pojo.TestDTO;
import org.woodwhale.poi.util.DownloadUtil;
import org.woodwhale.poi.util.PoiUtils;

@RestController
public class Demo6Controller {
	
	@RequestMapping("/demo6")
	public void demo6(HttpServletRequest request, HttpServletResponse response) {
		List<TestDTO> dataList = new ArrayList<>();
		dataList.add(new TestDTO(1, "王尼玛", 1, "C语言", 22));
		dataList.add(new TestDTO(1, "王尼玛", 1, "C++", 33));
		dataList.add(new TestDTO(2, "葫芦娃", 1, "Python", 50));
		dataList.add(new TestDTO(2, "葫芦娃", 1, "java", 44));
		dataList.add(new TestDTO(2, "葫芦娃", 1, "PHP", 66));
		dataList.add(new TestDTO(3, "佩奇", 0, "Python", 77));
		dataList.add(new TestDTO(3, "佩奇", 1, "java", 54));
		dataList.add(new TestDTO(3, "佩奇", 0, "PHP", 82));
		dataList.add(new TestDTO(4, "乔治", 0, "Python", 63));
		dataList.add(new TestDTO(4, "乔治", 0, "java", 77));
		dataList.add(new TestDTO(4, "乔治", 1, "PHP", 72));
		dataList.add(new TestDTO(5, "熊大", 1, "Python", 88));
		dataList.add(new TestDTO(5, "熊大", 1, "java", 91));
		dataList.add(new TestDTO(5, "熊大", 1, "PHP", 12));
		
		try {
			Workbook workbook = DownloadUtil.getWorkbook("测试", TestDTO.class, dataList);
			PoiUtils.downloadExcel(workbook, request, response, "注解开发测试");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
