import { Form, Input, Modal, Button, DatePicker, Select, message } from 'antd';
import { useEffect, useState } from 'react';
import { Semester } from '@/interfaces/semester/Semester';
import { useTranslations } from 'next-intl';
import moment from 'moment';

const { Option } = Select;

interface SemesterModalProps {
  visible: boolean;
  onCancel: () => void;
  onSubmit: (value: any) => void;
  semesterData?: Semester;
  isSubmitting?: boolean;
}

const SemesterModal = ({
  visible,
  onCancel,
  onSubmit,
  semesterData,
  isSubmitting,
}: SemesterModalProps) => {
  const [form] = Form.useForm();
  const [isEdit, setIsEdit] = useState<boolean>(false);
  const t = useTranslations('semester-management');
  const tCommon = useTranslations('common');
  const tValidation = useTranslations('validation');

  // Validate academic year format (YYYY-YYYY)
  const validateAcademicYear = (_: any, value: string) => {
    if (!value) return Promise.resolve();
    
    const pattern = /^\d{4}-\d{4}$/;
    if (!pattern.test(value)) {
      return Promise.reject(new Error(t('invalid-academic-year-format')));
    }
    
    // Check if years are consecutive
    const [firstYear, secondYear] = value.split('-').map(Number);
    if (secondYear !== firstYear + 1) {
      return Promise.reject(new Error(t('invalid-academic-year-consecutive')));
    }
    
    return Promise.resolve();
  };

  // Validate that dates are in correct order: startDate < lastCancelDate < endDate
  const validateDates = () => {
    const startDate = form.getFieldValue('startDate');
    const lastCancelDate = form.getFieldValue('lastCancelDate');
    const endDate = form.getFieldValue('endDate');
    
    if (!startDate || !lastCancelDate || !endDate) return;
    
    if (startDate.isAfter(lastCancelDate)) {
      form.setFields([{
        name: 'lastCancelDate',
        errors: [t('last-cancel-date-after-start')]
      }]);
    }
    
    if (lastCancelDate.isAfter(endDate)) {
      form.setFields([{
        name: 'lastCancelDate',
        errors: [t('last-cancel-date-before-end')]
      }]);
    }
  };

  // Initialize form with data when semesterData changes or modal becomes visible
  useEffect(() => {
    if (visible) {
      if (semesterData) {
        // For edit mode: populate form with existing data
        form.setFieldsValue({
          ...semesterData,
          // Convert string dates to moment objects for DatePicker
          startDate: semesterData.startDate ? moment(semesterData.startDate) : undefined,
          endDate: semesterData.endDate ? moment(semesterData.endDate) : undefined,
          lastCancelDate: semesterData.lastCancelDate ? moment(semesterData.lastCancelDate) : undefined,
        });
        setIsEdit(true);
      } else {
        // For create mode: reset form
        form.resetFields();
        setIsEdit(false);
      }
    }
  }, [semesterData, form, visible]);

  const handleSubmit = () => {
    form.validateFields()
      .then((values) => {
        // Convert moment objects to strings
        const formattedValues = {
          ...values,
          startDate: values.startDate ? values.startDate.format('YYYY-MM-DD') : undefined,
          endDate: values.endDate ? values.endDate.format('YYYY-MM-DD') : undefined,
          lastCancelDate: values.lastCancelDate ? values.lastCancelDate.format('YYYY-MM-DD') : undefined,
        };
        
        if (isEdit && semesterData) {
          formattedValues.id = semesterData.id;
        }
        
        onSubmit(formattedValues);
      })
      .catch(() => {
        message.error(t('check-info'));
      });
  };

  return (
    <Modal
      title={isEdit ? t('edit-semester') : t('add-semester')}
      open={visible}
      onCancel={() => {
        form.resetFields();
        onCancel();
      }}
      footer={[
        <Button key="cancel" onClick={onCancel}>
          {tCommon('cancel')}
        </Button>,
        <Button 
          key="submit" 
          type="primary" 
          onClick={handleSubmit}
          loading={isSubmitting}
        >
          {tCommon('save')}
        </Button>
      ]}
      destroyOnClose={true}
    >
      <Form form={form} layout="vertical" preserve={false}>
        <Form.Item
          label={t('semester-number')}
          name="semester"
          rules={[
            { required: true, message: t('required-semester-number') },
            { type: 'number', min: 1, max: 3, message: t('semester-number-range') }
          ]}
        >
          <Select>
            <Option value={1}>{t('semester-1')}</Option>
            <Option value={2}>{t('semester-2')}</Option>
            <Option value={3}>{t('semester-3')}</Option>
          </Select>
        </Form.Item>

        <Form.Item
          label={t('academic-year')}
          name="academicYear"
          rules={[
            { required: true, message: t('required-academic-year') },
            { validator: validateAcademicYear }
          ]}
        >
          <Input placeholder="2025-2026" />
        </Form.Item>

        <Form.Item
          label={t('start-date')}
          name="startDate"
          rules={[{ required: true, message: t('required-start-date') }]}
        >
          <DatePicker style={{ width: '100%' }} onChange={validateDates} />
        </Form.Item>

        <Form.Item
          label={t('end-date')}
          name="endDate"
          rules={[{ required: true, message: t('required-end-date') }]}
        >
          <DatePicker style={{ width: '100%' }} onChange={validateDates} />
        </Form.Item>

        <Form.Item
          label={t('last-cancel-date')}
          name="lastCancelDate"
          rules={[{ required: true, message: t('required-last-cancel-date') }]}
        >
          <DatePicker style={{ width: '100%' }} onChange={validateDates} />
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default SemesterModal;
