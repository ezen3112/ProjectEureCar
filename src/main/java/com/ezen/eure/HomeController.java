package com.ezen.eure;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezen.board.NoticePageDTO;
import com.ezen.board.CustnumPageDTO;
import com.ezen.board.NoticeService;


@Controller
public class HomeController {
	@Autowired
	SqlSession sqlSession;
	
	
	@RequestMapping(value = "/")
	public String main(HttpServletRequest request, Model mo) {
		HttpSession hs = request.getSession();
		hs.setAttribute("loginstate", false);
		hs.setAttribute("adminstate", false);
		
		   Service ser=sqlSession.getMapper(Service.class);
	       ArrayList<CarDTO> list=ser.bcselect();
	       mo.addAttribute("list", list);
	       
		return "main";
	}
	
	@RequestMapping(value="/index")
	public String ko4(HttpServletRequest request, Model mo) {
	
		   Service ser=sqlSession.getMapper(Service.class);
	       ArrayList<CarDTO> list=ser.bcselect();
	       mo.addAttribute("list", list);
		return "main";
	}
	
	@RequestMapping(value="/Joininput")
	public String join() {
		
		
		
		return "Joininput";
	}
	

	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public String hc2(HttpServletRequest request) {
		
		String name=request.getParameter("name");
		String sb=request.getParameter("sb");
		String address=request.getParameter("address");
		String id=request.getParameter("id");
		String pw=request.getParameter("pw");
		String email=request.getParameter("email");
		String birth=request.getParameter("birth");
		String tel=request.getParameter("tel");
		Service ser= sqlSession.getMapper(Service.class);
		ser.insert(name, sb, address, id, pw, email, birth, tel);
		return "login";
	}
	
	@RequestMapping(value="/logingo")
	public String logingo()
	{
		return "login";
	}
	

	
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request,  RedirectAttributes ra) {
		ModelAndView mav = new ModelAndView();
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String email = request.getParameter("email");
		Service ser = sqlSession.getMapper(Service.class);
		LoginDTO dto = ser.login(id,pw, email);
		AdminDTO dto2 = ser.login2(id,pw);
		if(dto!=null)
		{
			HttpSession hs = request.getSession();
			hs.setAttribute("member", dto);
			hs.setAttribute("loginstate", true);
			hs.setMaxInactiveInterval(300);
			mav.setViewName("redirect:index");
		}
		else if(dto2!=null)
		{
			HttpSession hs = request.getSession();
			hs.setAttribute("admin1", dto2);
			hs.setAttribute("adminstate", true);
			hs.setMaxInactiveInterval(300);
			mav.setViewName("redirect:index");
		}
		else 
		{
			ra.addAttribute("result","loginfail");
			mav.setViewName("redirect:logingo");
		}
		return mav;
	}
	@RequestMapping(value="/logout")
	public String logout(HttpServletRequest request)
	{
		HttpSession hs =request.getSession();
		hs.removeAttribute("member");
		hs.removeAttribute("admin1");
		hs.removeAttribute("loginstate");
		hs.removeAttribute("adminstate");
		hs.setAttribute("loginstate", false);
		hs.setAttribute("adminstate", false);
		return "redirect:index";
	}
	
	@RequestMapping(value="/carinput")
	public String ci(HttpServletRequest request, Model mo ,RedirectAttributes ra)
	{
        HttpSession hs=request.getSession();
        if(hs.getAttribute("loginstate").equals(true))
        {		
        	    return "carinput";
        }
        else
        {
           return "redirect:logingo3";
        }
	}
	
	@RequestMapping(value="/logingo3")
	public String lg3()
	{
		return "logincar";
	}
	
	@RequestMapping(value="/carlogin",method = RequestMethod.POST)
	public ModelAndView login3(HttpServletRequest request,  RedirectAttributes ra) {
		ModelAndView mav = new ModelAndView();
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String email = request.getParameter("email");		
		Service ser = sqlSession.getMapper(Service.class);
		LoginDTO dto = ser.login(id, pw, email);
		if(dto!=null)
		{
			HttpSession hs = request.getSession();
			hs.setAttribute("member", dto);
			hs.setAttribute("loginstate", true);
			hs.setMaxInactiveInterval(300);
			mav.setViewName("carinput");
		}
		else 
		{
			ra.addAttribute("result","loginfail");
			mav.setViewName("redirect:logingo");
		}
		return mav;
	}
	
	@RequestMapping(value = "/carsave", method = RequestMethod.POST)
    public String carsave(MultipartHttpServletRequest mul, HttpServletRequest request){        
		String carnum = mul.getParameter("carnum");
		String carbrand = mul.getParameter("carbrand");
		String carname = mul.getParameter("carname");
		String fueltype = mul.getParameter("fueltype");
		String origin = mul.getParameter("origin");
		String cyear = mul.getParameter("cyear");
		String color = mul.getParameter("color");
		int km = Integer.parseInt(mul.getParameter("km"));
		int price = Integer.parseInt(mul.getParameter("price"));
		String content = mul.getParameter("content");
		int custnum = Integer.parseInt(mul.getParameter("custnum"));
		String id = mul.getParameter("id");
        MultipartFile mf = mul.getFile("picture");
        MultipartFile mf2 = mul.getFile("picture2");
        MultipartFile mf3 = mul.getFile("picture3");
        String uploadPath = "";        
        String path =   "C:\\6spring\\eurecar_final\\src\\main\\webapp\\image\\";             
        String picture = mf.getOriginalFilename();            
        String picture2 = mf2.getOriginalFilename();            
        String picture3 = mf3.getOriginalFilename();            
        uploadPath = path+picture; 
        try {
            mf.transferTo(new File(uploadPath)); 
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
        Service ser= sqlSession.getMapper(Service.class);
        ser.carsave(carnum,carbrand,carname,fueltype,origin,cyear,color,km,price,content,picture,picture2,picture3,custnum,id);        
        return "redirect:index";
    }
	
	@RequestMapping(value = "/carout")
	public String carout(HttpServletRequest request,Model mo, PageDTO dto) {
		Service ser = sqlSession.getMapper(Service.class);
		String nowPage = request.getParameter("nowPage");
		String cntPerPage=request.getParameter("cntPerPage");
	    //?????? ???????????? ??????
	    int total=ser.cntkuk();
	    if(nowPage==null && cntPerPage == null) {
	       nowPage="1";
	       cntPerPage="5";
	    }
	    else if(nowPage==null) {
	       nowPage="1";
	    }
	    else if(cntPerPage==null) {
	       cntPerPage="5";
	    }      
	    dto=new PageDTO(total,Integer.parseInt(nowPage),Integer.parseInt(cntPerPage));
	    mo.addAttribute("paging",dto);
	    mo.addAttribute("view",ser.selectnotice(dto));
	    mo.addAttribute("hyundae",ser.hyundae(dto));
	    mo.addAttribute("ssang",ser.ssang(dto));
		return "carout";
	}
	
	@RequestMapping(value = "/carout2")
	public String carout2(HttpServletRequest request,Model mo, PageDTO dto) {
		Service ser = sqlSession.getMapper(Service.class);
		String nowPage = request.getParameter("nowPage");
		String cntPerPage=request.getParameter("cntPerPage");
		int total=ser.cntsu();
		if(nowPage==null && cntPerPage == null) {
		   nowPage="1";
		   cntPerPage="5";
		}
		else if(nowPage==null) {
		   nowPage="1";
		}
		else if(cntPerPage==null) {
		   cntPerPage="5";
		}
		dto=new PageDTO(total,Integer.parseInt(nowPage),Integer.parseInt(cntPerPage));
	    mo.addAttribute("paging",dto);
	    mo.addAttribute("view",ser.selectnotice2(dto));
	    return "carout2";
	}

	
	@RequestMapping(value = "/carsearch")
	public String search(HttpServletRequest request, Model mo, SearchDTO dto) {
		Service ser = sqlSession.getMapper(Service.class);
		String keyword = request.getParameter("keyword");
		String nowPage = request.getParameter("nowPage");
		String cntPerPage=request.getParameter("cntPerPage");
		mo.addAttribute("keyword", keyword);
		int total=ser.cntsearch(keyword);
		if(nowPage==null && cntPerPage == null) {
		   nowPage="1";
		   cntPerPage="5";
		}
		else if(nowPage==null) {
		   nowPage="1";
		}
		else if(cntPerPage==null) {
		   cntPerPage="5";
		}
		dto=new SearchDTO(keyword, total,Integer.parseInt(nowPage),Integer.parseInt(cntPerPage));
	    mo.addAttribute("paging",dto);
	    mo.addAttribute("view",ser.selectnotice3(dto));
		return "carsearch";
	}

	@RequestMapping(value = "/cardetail")
	public String cd(HttpServletRequest request,Model mo) {
		String carnum = request.getParameter("carnum");
		Service ser = sqlSession.getMapper(Service.class);
		CarDTO dto = ser.cardetail(carnum);
		mo.addAttribute("dto", dto);
		 ser.carreadcnt(carnum);
		return "cardetail";
	}
	//????????????   
	@RequestMapping(value = "/mclist")
	public String mclist(HttpServletRequest request,Model mo, RedirectAttributes ra,CustnumPageDTO2 dto) 
	{
		HttpSession hs=request.getSession();
		if(hs.getAttribute("loginstate").equals(true))
		{		
			Service ser = sqlSession.getMapper(Service.class);
			String id = request.getParameter("id");
		    String nowPage = request.getParameter("nowPage");
		    String cntPerPage=request.getParameter("cntPerPage");
			int total=ser.cntall(id);
			if(nowPage==null && cntPerPage == null) {
			   nowPage="1";
			   cntPerPage="5";
			}
			else if(nowPage==null) {
			   nowPage="1";
			}
			else if(cntPerPage==null) {
			   cntPerPage="5";
			}			
			dto=new CustnumPageDTO2(id,total,Integer.parseInt(nowPage),Integer.parseInt(cntPerPage));
		    mo.addAttribute("paging",dto);
		    mo.addAttribute("view",ser.mycarlist(dto));
		    mo.addAttribute("id", id);
			return "mycarlist";
		}
		else
		{
			return "redirect:logingo5";
		}
	}
	
	@RequestMapping(value="/logingo5")
	public String lg5()
	{
		return "logincarlist";
	}
	
	@RequestMapping(value="/mycarlistlogin",method = RequestMethod.POST)
	public ModelAndView mycarlistlogin(HttpServletRequest request, RedirectAttributes ra, Model mo, CustnumPageDTO2 dto2) {
		ModelAndView mav = new ModelAndView();
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String email = request.getParameter("email");
		Service ser = sqlSession.getMapper(Service.class);
		LoginDTO dto = ser.login(id, pw, email);
		if(dto!=null)
		{
			HttpSession hs = request.getSession();
			hs.setAttribute("member", dto);
			hs.setAttribute("loginstate", true);
			hs.setMaxInactiveInterval(300);
			String nowPage = request.getParameter("nowPage");
			String cntPerPage=request.getParameter("cntPerPage");
			int total=ser.cntall(id);
			if(nowPage==null && cntPerPage == null) {
				nowPage="1";
				cntPerPage="5";
			}
			else if(nowPage==null) {
				nowPage="1";
			}
			else if(cntPerPage==null) {
				cntPerPage="5";
			}			
			dto2=new CustnumPageDTO2(id,total,Integer.parseInt(nowPage),Integer.parseInt(cntPerPage));
			mo.addAttribute("paging",dto2);
			mo.addAttribute("view",ser.mycarlist(dto2));
			mo.addAttribute("id", id);
			mav.setViewName("mycarlist");
		}
		else 
		{
			ra.addAttribute("result","loginfail");
			mav.setViewName("redirect:logingo");
		}
		return mav;
	}
	
	@RequestMapping(value="/cardeletemid")
	public String cardeletemid(HttpServletRequest request,Model mo) {
		String carnum = request.getParameter("carnum");
		Service ser = sqlSession.getMapper(Service.class);
		CarDTO dto = ser.cardeletemid(carnum);
		mo.addAttribute("dto", dto);
		return "cardeletemid";
	}
	   
	@RequestMapping(value="/carupdatemid")
	public String carupdatemid(HttpServletRequest request,Model mo) {
		String carnum = request.getParameter("carnum");
		Service ser = sqlSession.getMapper(Service.class);
		CarDTO dto = ser.carupdatemid(carnum);
		mo.addAttribute("dto", dto);
		return "carupdatemid";
	}

	@RequestMapping(value="/cardelete")
	public String cardelete(HttpServletRequest request,Model mo,CustnumPageDTO2 dto) {
		String carnum = request.getParameter("carnum");
		String id = request.getParameter("id");
		Service ser = sqlSession.getMapper(Service.class);
		ser.cardelete(carnum);
		HttpSession hs=request.getSession();
		if(hs.getAttribute("loginstate").equals(true))
		{		
		    String nowPage = request.getParameter("nowPage");
		    String cntPerPage=request.getParameter("cntPerPage");
			int total=ser.cntall(id);
			if(nowPage==null && cntPerPage == null) {
			   nowPage="1";
			   cntPerPage="5";
			}
			else if(nowPage==null) {
			   nowPage="1";
			}
			else if(cntPerPage==null) {
			   cntPerPage="5";
			}			
			dto=new CustnumPageDTO2(id,total,Integer.parseInt(nowPage),Integer.parseInt(cntPerPage));
		    mo.addAttribute("paging",dto);
		    mo.addAttribute("view",ser.mycarlist(dto));
		    mo.addAttribute("id", id);
	   }
		return "mycarlist";
	}
	
	@RequestMapping(value = "/carupdate", method = RequestMethod.POST)
    public String carupdate(MultipartHttpServletRequest mul, HttpServletRequest request,Model mo,CustnumPageDTO2 dto){        
		String carnum = mul.getParameter("carnum");
		String carbrand = mul.getParameter("carbrand");
		String carname = mul.getParameter("carname");
		String fueltype = mul.getParameter("fueltype");
		String origin = mul.getParameter("origin");
		String cyear = mul.getParameter("cyear");
		String color = mul.getParameter("color");
		int km = Integer.parseInt(mul.getParameter("km"));
		int price = Integer.parseInt(mul.getParameter("price"));
		String content = mul.getParameter("content");
		int custnum = Integer.parseInt(mul.getParameter("custnum"));
		String id = mul.getParameter("id");
        MultipartFile mf = mul.getFile("picture");
        MultipartFile mf2 = mul.getFile("picture2");
        MultipartFile mf3 = mul.getFile("picture3");
        String uploadPath = "";        
        String path =  "C:\\6spring\\eurecar_final\\src\\main\\webapp\\image\\";            
        String picture = mf.getOriginalFilename();            
        String picture2 = mf2.getOriginalFilename();            
        String picture3 = mf3.getOriginalFilename();            
        uploadPath = path+picture; 
        try {
            mf.transferTo(new File(uploadPath)); 
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Service ser = sqlSession.getMapper(Service.class);
        ser.carupdate(carbrand,carname,fueltype,origin,cyear,color,km,price,content,picture,picture2,picture3,custnum,id,carnum); 
        HttpSession hs=request.getSession();
		if(hs.getAttribute("loginstate").equals(true))
		{		
		    String nowPage = request.getParameter("nowPage");
		    String cntPerPage=request.getParameter("cntPerPage");
			int total=ser.cntall(id);
			if(nowPage==null && cntPerPage == null) {
			   nowPage="1";
			   cntPerPage="5";
			}
			else if(nowPage==null) {
			   nowPage="1";
			}
			else if(cntPerPage==null) {
			   cntPerPage="5";
			}			
			dto=new CustnumPageDTO2(id,total,Integer.parseInt(nowPage),Integer.parseInt(cntPerPage));
		    mo.addAttribute("paging",dto);
		    mo.addAttribute("view",ser.mycarlist(dto));
		    mo.addAttribute("id", id);
		}
        return "mycarlist";
    }
	   //????????????
	   @RequestMapping(value="/sangdam")
	   public String sd(HttpServletRequest request, Model mo)
	   {
	       String id = request.getParameter("id");
	       String carnum = request.getParameter("carnum");
	       Service ser = sqlSession.getMapper(Service.class);
	       LoginDTO dto = ser.sangdam(id);
	       CarDTO dto2 = ser.sangdam2(carnum);
	       mo.addAttribute("member", dto);    
	       mo.addAttribute("dto", dto2);    
	       return "sangdam";
	   }
	   
	   @RequestMapping(value="/sangdamsave", method = RequestMethod.POST)
	   public String sdsv(HttpServletRequest request, Model mo)
	   {
	      String carnum = request.getParameter("carnum");
	      String carbrand = request.getParameter("carbrand");
	      String carname = request.getParameter("carname");
	      int price = Integer.parseInt(request.getParameter("price"));
	      String picture = request.getParameter("picture");
	      String name = request.getParameter("name");
	      String address = request.getParameter("address");
	      String tel = request.getParameter("tel");
	      int custnum = Integer.parseInt(request.getParameter("custnum"));
	      String id = request.getParameter("id");
	      Service ser = sqlSession.getMapper(Service.class);
	      ser.sangsave(carnum,carbrand,carname,price,picture,name,address,tel,custnum,id);
	      return "redirect:index";
	   }
	   
	   @RequestMapping(value="/mypage")
	   public String mp(HttpServletRequest request, Model mo,CustnumPageDTO2 dto) {
	      String id = request.getParameter("id");
	      Service ser = sqlSession.getMapper(Service.class);
	      String nowPage = request.getParameter("nowPage");
	      String cntPerPage=request.getParameter("cntPerPage");
	      int total=ser.cntall(id);
	      if(nowPage==null && cntPerPage == null) {
	         nowPage="1";
	         cntPerPage="5";
	      }
	      else if(nowPage==null) {
	         nowPage="1";
	      }
	      else if(cntPerPage==null) {
	         cntPerPage="5";
	      }
	      dto=new CustnumPageDTO2(id,total,Integer.parseInt(nowPage),Integer.parseInt(cntPerPage));
	      mo.addAttribute("paging",dto);
	      mo.addAttribute("view",ser.mypage(dto));
	      mo.addAttribute("id", id);
	      return "mypage";
	   }
	   
	   @RequestMapping(value="/sangdelete")
	   public String sdd(HttpServletRequest request, Model mo, CustnumPageDTO2 dto)
	   {
	      String id = request.getParameter("id");
	       Service ser = sqlSession.getMapper(Service.class);
	       ser.sangdelete(id);
	       String nowPage = request.getParameter("nowPage");
	       String cntPerPage=request.getParameter("cntPerPage");
	      int total=ser.cntall(id);
	      if(nowPage==null && cntPerPage == null) {
	         nowPage="1";
	         cntPerPage="5";
	      }
	      else if(nowPage==null) {
	         nowPage="1";
	      }
	      else if(cntPerPage==null) {
	         cntPerPage="5";
	      }         
	      dto=new CustnumPageDTO2(id,total,Integer.parseInt(nowPage),Integer.parseInt(cntPerPage));
	       mo.addAttribute("paging",dto);
	       mo.addAttribute("view",ser.mypage(dto));
	       mo.addAttribute("id", id);       
	       return "redirect:mypage";
	   }
	   //????????????
	   @RequestMapping(value = "/compare")
	   public String cp(HttpServletRequest request,Model mo,CompareDTO dto) {
	      Service ser = sqlSession.getMapper(Service.class);
	      String carname = request.getParameter("carname");
	      String nowPage = request.getParameter("nowPage");
	      String cntPerPage=request.getParameter("cntPerPage");
	      mo.addAttribute("carname", carname);
	      int total=ser.cntcompare(carname);
	      if(nowPage==null && cntPerPage == null) {
	         nowPage="1";
	         cntPerPage="5";
	      }
	      else if(nowPage==null) {
	         nowPage="1";
	      }
	      else if(cntPerPage==null) {
	         cntPerPage="5";
	      }
	      dto=new CompareDTO(carname, total,Integer.parseInt(nowPage),Integer.parseInt(cntPerPage));
	       mo.addAttribute("paging",dto);
	       mo.addAttribute("view",ser.compare(dto));
	      return "compare";
	   }
	   //????????????
	 //???????????? ?????????
	      @RequestMapping(value = "/addlist")
	      public String add(){
	         return "adcarinput";
	      }
	     
	      @RequestMapping(method = RequestMethod.POST,value = "/howsave")
		   public String howsave(MultipartHttpServletRequest mul){

		      String hname =mul.getParameter("hname");
		      MultipartFile mf = mul.getFile("photo");
		      String htitle =mul.getParameter("htitle");
		      String hcontent =mul.getParameter("hcontent");
		      String good =mul.getParameter("good");
		      String bad =mul.getParameter("bad");
		      String score =mul.getParameter("score");
		      Service ser= sqlSession.getMapper(Service.class);
		      
		        String uploadPath = "";        
		        String path =  "C:\\6spring\\eurecar_final\\src\\main\\webapp\\image\\";            
		        String photo = mf.getOriginalFilename();            
		        uploadPath = path+photo; 
		        try {
		            mf.transferTo(new File(uploadPath)); 
		        } catch (IllegalStateException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        } catch (IOException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }        
		      ser.howsave(photo,hname,htitle,good,bad,hcontent,score);
		      return "redirect:index";
		   }
		  
	      
	      
	      //???????????? ??????
	      @RequestMapping(value="/howlist")
		   public String howlist(HttpServletRequest request,Model mo) {
			   	HttpSession session=request.getSession();
				   
				if( (Boolean) session.getAttribute("adminstate"))
				{	
					
					//
					Service ser=sqlSession.getMapper(Service.class);
					ArrayList<HowDTO> list=ser.howlistselect();
					mo.addAttribute("list",list);
					//
					//????????? ????????????
					String id = request.getParameter("id");
					AdminDTO adto = ser.howlistgetid(id);
					mo.addAttribute("adto",adto);
					//????????? ????????????
					return "hosthowlist";
				}
				else if( (Boolean) session.getAttribute("loginstate"))
				{	   
					Service ser=sqlSession.getMapper(Service.class);
					ArrayList<HowDTO> list=ser.howlistselect();
					mo.addAttribute("list",list);
					
					//????????? ????????????
					String id = request.getParameter("memberid");
					LoginDTO ldto = ser.howlistgetmemberid(id);
					mo.addAttribute("ldto",ldto);
					//????????? ????????????
					return "howlist";
				}
					Service ser=sqlSession.getMapper(Service.class);
				    ArrayList<HowDTO> list=ser.howlistselect();
				    mo.addAttribute("list",list);
					return "howlist";
		   		}
	    
	         //???????????? ??????????????? 
	         @RequestMapping(value="/howdetail")
	         public String howdetail(HttpServletRequest request,Model mo) {
	           HttpSession session=request.getSession();
	            if( (Boolean) session.getAttribute("adminstate"))
	            {   
	               //??????????????? ?????? ????????????
	               String hname = request.getParameter("hname");
	               Service ser=sqlSession.getMapper(Service.class);
	               HowDTO hdto = ser.howdetail(hname);
	               mo.addAttribute("hdto",hdto);
	               //??????????????? ?????? ????????????
	               //????????? ???????????? ????????????
	               String id = request.getParameter("id");
	               AdminDTO adto = ser.howlistgetid(id);
	               mo.addAttribute("adto",adto);
	               //????????? ???????????? ????????????
	                //???????????????????????????
	               int hcarnum = Integer.parseInt(request.getParameter("hcarnum"));
	               ArrayList<ReplyDTO> list=ser.selectreply(hcarnum);
	               mo.addAttribute("list", list);
	                //???????????????????????????
	               return "hosthowdetail";
	            }
	            else if( (Boolean) session.getAttribute("loginstate"))
	            {      
	               //??????????????? ?????? ????????????
	               String hname = request.getParameter("hname");
	               Service ser=sqlSession.getMapper(Service.class);
	               HowDTO hdto = ser.howdetail(hname);
	               mo.addAttribute("hdto",hdto);
	               //??????????????? ?????? ????????????
	               //????????? ???????????? ????????????
	               String id = request.getParameter("id");
	               LoginDTO ldto = ser.howlistgetmemberid(id);
	               mo.addAttribute("ldto",ldto);
	               //????????? ???????????? ????????????
	                //???????????????????????????
	               int hcarnum = Integer.parseInt(request.getParameter("hcarnum"));
	               ArrayList<ReplyDTO> list=ser.selectreply(hcarnum);
	               mo.addAttribute("list", list);
	                //???????????????????????????
	               return "memberhowdetail";
	            }
	            
	               //??????????????? ??????
	            //??????????????? ?????? ????????????
	            String hname = request.getParameter("hname");
	            Service ser=sqlSession.getMapper(Service.class);
	            HowDTO hdto = ser.howdetail(hname);
	            mo.addAttribute("hdto",hdto);
	            //??????????????? ?????? ????????????
	            //????????? ???????????? ????????????
	            String id = request.getParameter("id");
	            AdminDTO adto = ser.howlistgetid(id);
	            mo.addAttribute("adto",adto);
	            //????????? ???????????? ????????????
	             //???????????????????????????
	            int hcarnum = Integer.parseInt(request.getParameter("hcarnum"));
	            ArrayList<ReplyDTO> list=ser.selectreply(hcarnum);
	            mo.addAttribute("list", list);
	             //???????????????????????????
	               return "howdetail";
	         
	      }
	         //???????????? ????????????
	         @RequestMapping(value = "/replysave")
	         public String replysave(HttpServletRequest request,Model mo){
	            HttpSession session=request.getSession();
	            if( (Boolean) session.getAttribute("adminstate"))
	            {   
	            String id=request.getParameter("id");
	            String rcontent=request.getParameter("rcontent"); 
	            int hcarnum = Integer.parseInt(request.getParameter("hcarnum"));
	            Service ser=sqlSession.getMapper(Service.class);
	            ser.inputreply(id,rcontent,hcarnum);
	            
	            //??????????????? ?????? ????????????
	            String hname = request.getParameter("hname");
	            HowDTO hdto = ser.howdetail(hname);
	            mo.addAttribute("hdto",hdto);
	            //??????????????? ?????? ????????????
	            //????????? ???????????? ????????????
	            AdminDTO adto = ser.howlistgetid(id);
	            mo.addAttribute("adto",adto);
	            //????????? ???????????? ????????????
	             //???????????????????????????
	            ArrayList<ReplyDTO> list=ser.selectreply(hcarnum);
	            mo.addAttribute("list", list);
	             //???????????????????????????
	            return "hosthowdetail";
	            }
	            else if( (Boolean) session.getAttribute("loginstate"))
	            {      
	               String id=request.getParameter("id");
	               String rcontent=request.getParameter("rcontent"); 
	               int hcarnum = Integer.parseInt(request.getParameter("hcarnum"));
	               Service ser=sqlSession.getMapper(Service.class);
	               ser.inputreply(id,rcontent,hcarnum);
	               
	               //??????????????? ?????? ????????????
	               String hname = request.getParameter("hname");
	               HowDTO hdto = ser.howdetail(hname);
	               mo.addAttribute("hdto",hdto);
	               //??????????????? ?????? ????????????
	               //????????? ???????????? ????????????
	               LoginDTO ldto = ser.howlistgetmemberid(id);
	               mo.addAttribute("ldto",ldto);
	               //????????? ???????????? ????????????
	                //???????????????????????????
	               ArrayList<ReplyDTO> list=ser.selectreply(hcarnum);
	               mo.addAttribute("list", list);
	                //???????????????????????????
	            return "memberhowdetail";
	            }
	            return "howdetail";
	         }
	         
	         //???????????? ???????????????
	         @RequestMapping(value="/logingo4")
	         public String lg4(HttpServletRequest request, Model mo)
	         {   
	            int hcarnum = Integer.parseInt(request.getParameter("hcarnum"));
	            Service ser = sqlSession.getMapper(Service.class);
	            HowDTO hdto = ser.howlogingodetail(hcarnum);
	            mo.addAttribute("hdto",hdto);
	            
	            
	            return "loginhow";
	         }
	         @RequestMapping(value="/login4",method = RequestMethod.POST)
	         public  ModelAndView howlog(HttpServletRequest request,  RedirectAttributes ra, Model mo)
	         {
	            
	            ModelAndView mav = new ModelAndView();
	            String id = request.getParameter("id");
	            String pw = request.getParameter("pw");
	            String email = request.getParameter("email");
	            Service ser = sqlSession.getMapper(Service.class);
	            LoginDTO dto = ser.login(id, pw, email);
	            AdminDTO dto2 = ser.login2(id,pw);
	            
	            
	            
	            if(dto!=null)
	            {
	               //??????????????? ?????? ????????????
	               String hname = request.getParameter("hname");
	               HowDTO hdto = ser.howdetail(hname);
	               mo.addAttribute("hdto",hdto);
	               //??????????????? ?????? ????????????
	               //????????? ???????????? ????????????
	               LoginDTO ldto = ser.howlistgetmemberid(id);
	               mo.addAttribute("ldto",ldto);
	               //????????? ???????????? ????????????
	                //???????????????????????????
	               int hcarnum = Integer.parseInt(request.getParameter("hcarnum"));
	               ArrayList<ReplyDTO> list=ser.selectreply(hcarnum);
	               mo.addAttribute("list", list);
	                //???????????????????????????
	               HttpSession hs = request.getSession();
	               hs.setAttribute("member", dto);
	               hs.setAttribute("loginstate", true);
	               hs.setMaxInactiveInterval(300);
	               mav.setViewName("memberhowdetail");
	            }
	            else if(dto2!=null)
	            {
	               //??????????????? ?????? ????????????
	               String hname = request.getParameter("hname");
	               HowDTO hdto = ser.howdetail(hname);
	               mo.addAttribute("hdto",hdto);
	               //??????????????? ?????? ????????????
	               //????????? ???????????? ????????????
	               AdminDTO adto = ser.howlistgetid(id);
	               mo.addAttribute("adto",adto);
	               //????????? ???????????? ????????????
	                //???????????????????????????
	               int hcarnum = Integer.parseInt(request.getParameter("hcarnum"));
	               ArrayList<ReplyDTO> list=ser.selectreply(hcarnum);
	               mo.addAttribute("list", list);
	                //???????????????????????????
	               HttpSession hs = request.getSession();
	               hs.setAttribute("admin1", dto2);
	               hs.setAttribute("adminstate", true);
	               hs.setMaxInactiveInterval(300);
	               mav.setViewName("hosthowdetail");
	            }
	            else 
	            {   
	               ra.addAttribute("result","loginfail");
	               mav.setViewName("redirect:logingo4");
	            }
	            return mav;
	         }
	         
	         //????????????
	            @RequestMapping(value="/preference")
	            public String prefer(HttpServletRequest request,Model mo,PreferDTO dto) {
	               Service ser = sqlSession.getMapper(Service.class);
	               String prefer = request.getParameter("prefer");
	               String nowPage = request.getParameter("nowPage");
	               String cntPerPage=request.getParameter("cntPerPage");
	                //?????? ???????????? ??????
	                int total=ser.cntprefer(prefer);
	                if(nowPage==null && cntPerPage == null) {
	                   nowPage="1";
	                   cntPerPage="5";
	                }
	                else if(nowPage==null) {
	                   nowPage="1";
	                }
	                else if(cntPerPage==null) {
	                   cntPerPage="5";
	                }      
	                dto=new PreferDTO(prefer,total,Integer.parseInt(nowPage),Integer.parseInt(cntPerPage));
	                mo.addAttribute("paging",dto);
	                mo.addAttribute("view",ser.prefer(dto));
	                mo.addAttribute("prefer", prefer);
	               return "preferout";
	            }
	            
	       
}
