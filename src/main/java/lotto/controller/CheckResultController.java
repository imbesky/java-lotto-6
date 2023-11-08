package lotto.controller;

import java.util.List;
import lotto.constant.ConstantNumber;
import lotto.model.BoughtLotto;
import lotto.model.Lotto;
import lotto.model.Result;
import lotto.model.WinningNumbers;
import lotto.service.CheckResultService;

public class CheckResultController {

    private final CheckResultService lottoResultService = new CheckResultService();
    private final BoughtLotto boughtLotto;
    private final WinningNumbers winningNumbers;
    private final Result result;

    public CheckResultController(BoughtLotto boughtLotto, WinningNumbers winningNumbers, Result result) {
        this.boughtLotto = boughtLotto;
        this.winningNumbers = winningNumbers;
        this.result = result;
    }

    public void checkMatchResult() {
        for (Lotto lotto : boughtLotto.getAllLotto()) {
            int matched = lottoResultService.matchResult(lotto.getNumbers(), winningNumbers.getWinningNumbers());
            if (lottoResultService.inWinRange(matched)) {
                result.addWinInformation(matched);
                checkIfFiveMatchWithBonus(lotto.getNumbers());
            }
        }
    }

    private void checkIfFiveMatchWithBonus(List<Integer> lottoNumber) {
        if (lottoResultService.isBonusNumberIncluded(lottoNumber, winningNumbers.getBonusNumber())) {
            result.addFiveMatchWithBonus();
        }
    }

    public void checkEarningRate() {
        int earning = lottoResultService.calculateEarning(result.getMatchResult());
        result.calculateEarningRate(earning, boughtLotto.getBoughtNumber() * ConstantNumber.LOTTO_PRICE_UNIT);
    }

}
