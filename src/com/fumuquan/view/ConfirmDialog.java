package com.fumuquan.view;

import com.fumuquan.R;
import com.fumuquan.util.DensityUtil;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 确定对话框. 有确定，取消两个按钮。点击按钮时执行相应逻辑，对话框消失
 * 
 * @author conwaywang
 * 
 */
public class ConfirmDialog extends Dialog {
	private Button btnConfirm;
	private Button btnCancel;
	private TextView content;
	private View view;

	private Context context;
	private String mConfirmContent;
	private ConfirmProcess mConfirmProcess;
	private boolean isDissmiss;

	/**
	 * 确定对话框. 有确定，取消两个按钮。点击按钮时执行相应逻辑，对话框消失
	 * 
	 * @param context
	 * @param confirmContent
	 *            对话框显示内容
	 * @param confirmProcess
	 *            确定按钮处理内容
	 * */
	public ConfirmDialog(Context context, final String confirmContent, final ConfirmProcess confirmProcess) {
		super(context, R.style.myDialog);

		this.context = context;
		this.mConfirmContent = confirmContent;
		this.mConfirmProcess = confirmProcess;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCancelable(false);

		view = LayoutInflater.from(context).inflate(R.layout.confirm_dialog,
				null);

		// findView
		btnConfirm = (Button) view.findViewById(R.id.btn_confirm);
		btnCancel = (Button) view.findViewById(R.id.btn_cancel);
		content = (TextView) view.findViewById(R.id.content);
		content.setText(mConfirmContent);

		// setupListener
		btnConfirm.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// 确定逻辑
				mConfirmProcess.doConfirm();
				
				dismiss();
			}
		});

		btnCancel.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// 取消逻辑
				mConfirmProcess.doCancel();

				ConfirmDialog.this.dismiss();

			}
		});

		setContentView(view);
		getWindow().setLayout(DensityUtil.dip2px(context, 265),
				DensityUtil.dip2px(context, 100));
	}

	@Override
	public void dismiss() {
		super.dismiss();
	}

	/**
	 * 确定或者取消时的处理逻辑
	 * 
	 * @author conwaywang
	 * 
	 */
	public interface ConfirmProcess {
		public void doConfirm();

		public void doCancel();
	}
}
