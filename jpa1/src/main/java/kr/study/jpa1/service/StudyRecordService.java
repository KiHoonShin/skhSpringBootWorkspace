package kr.study.jpa1.service;

import kr.study.jpa1.domain.Member;
import kr.study.jpa1.domain.StudyRecord;
import kr.study.jpa1.form.StudyForm;
import kr.study.jpa1.repository.StudyRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyRecordService {
    private final StudyRecordRepository recordRepository;

    @Transactional
    public void saveRecord(StudyForm form, Member member){
        StudyRecord record = StudyRecord.createRecord(form, member);
        recordRepository.save(record);
    }


    public List<StudyRecord> testAll(){
        return recordRepository.findAll();
    }

    public StudyRecord getOneRecord(Long studyId){
        Optional<StudyRecord> record = recordRepository.findById(studyId);
        return record.isPresent()? record.get() : null;
    }

    @Transactional
    public void updateRecord(StudyForm form, StudyRecord record){
        StudyRecord updateRecord = StudyRecord.modifyRecord(form , record);
        recordRepository.save(updateRecord);
    }

    @Transactional
    public void deleteRecord(Long studyId){
        recordRepository.deleteById(studyId);
    }
    public List<StudyRecord> getAllRecords(){
        return recordRepository.selectAll();
    }

    @Transactional
    public void deleteAllRecordByMember(Member member){
        List<StudyRecord> list = recordRepository.searchStudyRecordByMemberId(member.getId());
        if(list != null){
            list.forEach(record -> {
                log.trace("delete record = {}" , record);
                recordRepository.deleteByMember(record.getMember());
            });
        }
    }

}
