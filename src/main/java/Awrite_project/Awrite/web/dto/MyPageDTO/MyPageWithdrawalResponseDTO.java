package Awrite_project.Awrite.web.dto.MyPageDTO;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class MyPageWithdrawalResponseDTO {
    private String msg;

     public MyPageWithdrawalResponseDTO(String msg){
         this.msg = msg;
     }
}
